package logic.getHealthyFastFoodTest

import MealHelperTest.createNutrition
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import logic.getHealthyFastFoodMeals.GetHealthyFastFoodMealsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.getHealthyFastFoodTest.testData.FakeDataMeals
import test.logic.getHealthyFastFoodTest.createMeal

class GetHealthyFastFoodMealsUseCaseTest {
    private lateinit var gettingHealthFooduseCase: GetHealthyFastFoodMealsUseCase
    private lateinit var mealsDataSource: MealsDataSource

    @BeforeEach
    fun setup() {
        mealsDataSource = mockk(relaxed = true)
        gettingHealthFooduseCase = GetHealthyFastFoodMealsUseCase(mealsDataSource)
    }

    @Test
    fun `getHealthyFastFoodMeals should return meals with prep time less than 15 and complete nutrition`() {
        every { mealsDataSource.getAllMeals() } returns FakeDataMeals.allSamplesMealsCase

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).containsExactly(
            FakeDataMeals.healthyMeal
        )
    }

    @Test
    fun `getHealthyFastFoodMeals should return empty array when there's no meals on mealsDataSource`() {
        every { mealsDataSource.getAllMeals() } returns emptyList()

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).isEmpty()
    }

    @Test
    fun `getHealthyFastFoodMeals should return empty array when no meals pass condtions of healthy food`() {
        every { mealsDataSource.getAllMeals() } returns FakeDataMeals.invalidHealthyFood

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).isEmpty()
    }

    @Test
    fun `getHealthyFastFoodMeals should ignore meals with null nutrition values`() {
        every { mealsDataSource.getAllMeals() } returns listOf(
            FakeDataMeals.mealWithNullNutritionValue,
            FakeDataMeals.healthyMeal
        )

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).containsExactly(FakeDataMeals.healthyMeal)
    }

    @Test
    fun `getHealthyFastFoodMeals should ignore meals with preparation time more than 15 minutes`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FakeDataMeals.longPrepMeal)

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).isEmpty()
    }

    // check branches for fat and carbo
    @Test
    fun `getHealthyFastFoodMeals should ignore meals with null saturatedFat`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FakeDataMeals.mealWithNullSaturatedFat)

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).isEmpty()
    }

    @Test
    fun `getHealthyFastFoodMeals should ignore meals with null carbohydrates`() {

        every { mealsDataSource.getAllMeals() } returns listOf(FakeDataMeals.mealWithNullableCarb)

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).isEmpty()
    }

    @Test
    fun `getHealthyFastFoodMeals should exclude meals that exceed average fat or carbs`() {
        val healthyMeal = createMeal(
            id = 1,
            name = "Valid Meal",
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 5f,
                saturatedFat = 1f,
                carbohydrates = 10f
            )
        )
        val highFatMeal = createMeal(
            id = 2,
            name = "Too Fatty",
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 20f,
                saturatedFat = 5f,
            ))

        every { mealsDataSource.getAllMeals() } returns listOf(healthyMeal, highFatMeal)

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).containsExactly(healthyMeal)
    }

    // adde d
    @Test
    fun `getHealthyFastFoodMeals should handle meal with exactly 15 minutes prep time`() {
        val boundaryTimeMeal = createMeal(
            id = 1,
            name = "Boundary Time Meal",
            minutes = 15,  // exactly MAX_PREPARATION_TIME
            nutrition = createNutrition(
                totalFat = 5f,
                saturatedFat = 1f,
                carbohydrates = 10f
            )
        )
        every { mealsDataSource.getAllMeals() } returns listOf(boundaryTimeMeal)

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).containsExactly(boundaryTimeMeal)
    }

    @Test
    fun `getHealthyFastFoodMeals should return empty when no meals pass initial filter`() {
        val meals = listOf(
            createMeal(minutes = null),  // null minutes
            createMeal(nutrition = createNutrition(totalFat = null)),  // null totalFat
            createMeal(nutrition = createNutrition(saturatedFat = null)),  // null saturatedFat
            createMeal(nutrition = createNutrition(carbohydrates = null)),  // null carbs
            createMeal(minutes = 20)  // over time limit
        )
        every { mealsDataSource.getAllMeals() } returns meals

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).isEmpty()
    }

    @Test
    fun `getHealthyFastFoodMeals should handle single valid meal`() {
        val singleMeal = createMeal(
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 5f,
                saturatedFat = 2f,
                carbohydrates = 10f
            )
        )
        every { mealsDataSource.getAllMeals() } returns listOf(singleMeal)

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).containsExactly(singleMeal)
    }

    @Test
    fun `getHealthyFastFoodMeals should handle meals with nutrition values equal to averages`() {
        val meal1 = createMeal(
            id = 1,
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 5f,
                saturatedFat = 2f,
                carbohydrates = 10f
            )
        )
        val meal2 = createMeal(
            id = 2,
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 5f,
                saturatedFat = 2f,
                carbohydrates = 10f
            )
        )
        every { mealsDataSource.getAllMeals() } returns listOf(meal1, meal2)

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).containsExactly(meal1, meal2)
    }

    // another added
    @Test
    fun `getHealthyFastFoodMeals should handle empty list after initial filtering when calculating averages`() {
        // All meals fail initial filter
        val meals = listOf(
            createMeal(minutes = null),
            createMeal(minutes = 20),
            createMeal(nutrition = createNutrition(totalFat = null))
        )
        every { mealsDataSource.getAllMeals() } returns meals

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).isEmpty()
    }


    @Test
    fun `getHealthyFastFoodMeals should include meal when nutrition values are exactly average`() {
        val meal1 = createMeal(
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 5f,
                saturatedFat = 2f,
                carbohydrates = 10f
            )
        )
        val meal2 = createMeal(
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 5f,
                saturatedFat = 2f,
                carbohydrates = 10f
            )
        )
        every { mealsDataSource.getAllMeals() } returns listOf(meal1, meal2)

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        // Both should be included as they're exactly at average
        assertThat(result).containsExactly(meal1, meal2)
    }

    @Test
    fun `getHealthyFastFoodMeals should handle case where all nutrition values are zero`() {
        val zeroNutritionMeal = createMeal(
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 0f,
                saturatedFat = 0f,
                carbohydrates = 0f
            )
        )
        every { mealsDataSource.getAllMeals() } returns listOf(zeroNutritionMeal)

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).containsExactly(zeroNutritionMeal)
    }

    @Test
    fun `getHealthyFastFoodMeals should return empty when some nutrition values are NaN`() {
        val meal1 = createMeal(
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 5f,
                saturatedFat = Float.NaN,
                carbohydrates = 10f
            )
        )

        val meal2 = createMeal(
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 6f,
                saturatedFat = Float.NaN,
                carbohydrates = 12f
            )
        )

        every { mealsDataSource.getAllMeals() } returns listOf(meal1, meal2)

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).isEmpty()
    }


}