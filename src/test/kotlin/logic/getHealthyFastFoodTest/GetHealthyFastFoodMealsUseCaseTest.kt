package logic.getHealthyFastFoodTest

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import logic.getHealthyFastFoodMeals.GetHealthyFastFoodMealsUseCase
import logic.getHealthyFastFoodTest.testData.FakeDataMeals
import model.Meal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class GetHealthyFastFoodMealsUseCaseTest {
    private lateinit var getHealthFoodUseCase: GetHealthyFastFoodMealsUseCase
    private lateinit var mealsDataSource: MealsDataSource

    @BeforeEach
    fun setup() {
        mealsDataSource = mockk(relaxed = true)
        getHealthFoodUseCase = GetHealthyFastFoodMealsUseCase(mealsDataSource)
    }

    @Test
    fun `getHealthyFastFoodMeals should return meals with prep time less than 15 and complete nutrition`() {
        every { mealsDataSource.getAllMeals() } returns FakeDataMeals.allSamplesMealsCase

        val result = getHealthFoodUseCase.getHealthyFastFoodMeals()

        assertThat(result).containsExactly(FakeDataMeals.healthyMeal)
    }

   private companion object {
        @JvmStatic
        fun emptyOrInvalidMealsProvider(): List<List<Meal>> = listOf(
            emptyList(),
            FakeDataMeals.invalidHealthyFood,
            listOf(FakeDataMeals.mealWithNullNutritionValue),
            listOf(FakeDataMeals.longPreparationTimeMeal),
            listOf(FakeDataMeals.mealWithNullSaturatedFat),
            listOf(FakeDataMeals.mealWithNullCarb),
            FakeDataMeals.mealsWithMissingOrInvalidData,
            FakeDataMeals.invalidNaNMeals
        )
    }

    @ParameterizedTest
    @MethodSource("emptyOrInvalidMealsProvider")
    fun `getHealthyFastFoodMeals should return empty when meals are empty or invalid`(meals: List<Meal>) {
        every { mealsDataSource.getAllMeals() } returns meals

        val result = getHealthFoodUseCase.getHealthyFastFoodMeals()

        assertThat(result).isEmpty()
    }

    @Test
    fun `getHealthyFastFoodMeals should exclude meals with nutrition values exactly above average`() {
        every { mealsDataSource.getAllMeals() } returns listOf(
            FakeDataMeals.healthyMeal,
            FakeDataMeals.mealWithFatJustAboveAverage,
            FakeDataMeals.mealWithCarbsJustAboveAverage
        )

        val result = getHealthFoodUseCase.getHealthyFastFoodMeals()

        assertThat(result).containsExactly(FakeDataMeals.healthyMeal)
    }

    @Test
    fun `getHealthyFastFoodMeals should handle meals with some null and some valid nutrition values`() {
        every { mealsDataSource.getAllMeals() } returns listOf(
            FakeDataMeals.mealWithNullFatButValidCarbs, FakeDataMeals.mealWithNullCarbsButValidFat
        )
        val result = getHealthFoodUseCase.getHealthyFastFoodMeals()

        assertThat(result).isEmpty()
    }

    @Test
    fun `getHealthyFastFoodMeals should handle floating point averages`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FakeDataMeals.slightlyAbove)
        assertThat(getHealthFoodUseCase.getHealthyFastFoodMeals()).isEmpty()
    }

}
