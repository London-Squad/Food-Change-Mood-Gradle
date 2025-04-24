package logic.ketoMealHelperUseCaseTest

import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import logic.ketoMealHelper.GetKetoMealUseCase
import mealHelperTest.createMeal
import mealHelperTest.createNutrition
import model.Meal
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

class GetKetoMealUseCaseTest {

    private lateinit var getKetoMealUseCase: GetKetoMealUseCase
    private lateinit var mealsDataSource: MealsDataSource

    @BeforeEach
    fun setup() {
        mealsDataSource = mockk(relaxed = true)
        getKetoMealUseCase = GetKetoMealUseCase(mealsDataSource)
    }

    @Test
    fun `suggestMeal returns null when no meals available`() {
        every { mealsDataSource.getAllMeals() } returns emptyList()
        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal returns valid keto meal when all nutrition values meet the requirements`() {
        val validNutrition = createNutrition(
            calories = 500f,
            carbohydrates = 5f,
            sugar = 2f,
            totalFat = 40f,
            protein = 32f,
            saturatedFat = 10f
        )

        val validMeal = createMeal(
            id = 9,
            nutrition = validNutrition,
            ingredients = listOf("chicken", "avocado")
        )

        every { mealsDataSource.getAllMeals() } returns listOf(validMeal)

        val result = getKetoMealUseCase.suggestMeal()

        assertNotNull(result)
        assertEquals(validMeal, result)
    }


    /// test all edge cases for isPassesKetoNutritionCheck
    @Test
    fun `suggestMeal skips meal when values of nutrition high and does not meet keto requirements`() {
        every { mealsDataSource.getAllMeals() } returns emptyList()

        val invalidNutrition = createNutrition(
            calories = 1000f,
            carbohydrates = 50f,
            sugar = 10f,
            totalFat = 40f,
            protein = 20f,
            saturatedFat = 30f
        )

        val invalidMeal = createMeal(
            id = 8,
            nutrition = invalidNutrition,
            ingredients = listOf("chicken", "avocado")
        )

        every { mealsDataSource.getAllMeals() } returns listOf(invalidMeal)

        val result = getKetoMealUseCase.suggestMeal()
        assertNull(result)
    }
    @Test
    fun `suggestMeal skips meal when values of nutrition it's all null`() {
        every { mealsDataSource.getAllMeals() } returns emptyList()

        val invalidNutrition = createNutrition(
            calories = null,
            carbohydrates = null,
            sugar = null,
            totalFat = null,
            protein = null,
            saturatedFat = null
        )

        val invalidMeal = createMeal(
            id = 8,
            nutrition = invalidNutrition,
            ingredients = listOf("chicken", "avocado")
        )

        every { mealsDataSource.getAllMeals() } returns listOf(invalidMeal)

        val result = getKetoMealUseCase.suggestMeal()
        assertNull(result)
    }
    @Test
    fun `suggestMeal skips meal when calories is null`() {
        val meal = createMeal(
            id = 1,
            nutrition = createNutrition(
                calories = null,
                carbohydrates = 20f,
                sugar = 2f,
                totalFat = 70f,
                protein = 40f,
                saturatedFat = 15f
            ),
            ingredients = listOf("chicken")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(meal)
        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal skips meal when carbohydrates is null`() {
        val meal = createMeal(
            id = 2,
            nutrition = createNutrition(
                calories = 500f,
                carbohydrates = null,
                sugar = 2f,
                totalFat = 70f,
                protein = 40f,
                saturatedFat = 15f
            ),
            ingredients = listOf("chicken")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(meal)
        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal skips meal when sugar is null`() {
        val meal = createMeal(
            id = 3,
            nutrition = createNutrition(
                calories = 500f,
                carbohydrates = 20f,
                sugar = null,
                totalFat = 70f,
                protein = 40f,
                saturatedFat = 15f
            ),
            ingredients = listOf("chicken")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(meal)
        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal skips meal when totalFat is null`() {
        val meal = createMeal(
            id = 4,
            nutrition = createNutrition(
                calories = 500f,
                carbohydrates = 20f,
                sugar = 2f,
                totalFat = null,
                protein = 40f,
                saturatedFat = 15f
            ),
            ingredients = listOf("chicken")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(meal)
        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal skips meal when protein is null`() {
        val meal = createMeal(
            id = 5,
            nutrition = createNutrition(
                calories = 500f,
                carbohydrates = 20f,
                sugar = 2f,
                totalFat = 70f,
                protein = null,
                saturatedFat = 15f
            ),
            ingredients = listOf("chicken")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(meal)
        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal skips meal when saturatedFat is null`() {
        val meal = createMeal(
            id = 6,
            nutrition = createNutrition(
                calories = 500f,
                carbohydrates = 20f,
                sugar = 2f,
                totalFat = 70f,
                protein = 40f,
                saturatedFat = null
            ),
            ingredients = listOf("chicken")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(meal)
        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal skips meal when saturatedFat too low and percent not in allowed range`() {
        val meal = createMeal(
            id = 7,
            nutrition = createNutrition(
                calories = 500f,
                carbohydrates = 20f,
                sugar = 2f,
                totalFat = 70f,
                protein = 40f,
                saturatedFat = 1f
            ),
            ingredients = listOf("chicken")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(meal)
        assertNull(getKetoMealUseCase.suggestMeal())
    }
    @Test
    fun `suggestMeal skips meal when saturatedFat too high and percent not in allowed range`() {
        val meal = createMeal(
            id = 7,
            nutrition = createNutrition(
                calories = 500f,
                carbohydrates = 20f,
                sugar = 2f,
                totalFat = 70f,
                protein = 40f,
                saturatedFat = 45f
            ),
            ingredients = listOf("chicken")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(meal)
        assertNull(getKetoMealUseCase.suggestMeal())
    }

//  --- here invalid cases for
val disallowedItems = listOf(
    "bread", "pasta", "rice", "potato", "sugar", "high fructose corn syrup",
    "corn", "wheat", "flour", "legume", "bean", "soda", "milk", "skim milk",
    "honey", "agave", "cereal", "oat", "quinoa", "yogurt", "juice"
)

    @Test
    fun `suggestMeal returns null for all meals with disallowed ingredients`() {
        val validNutrition = createNutrition(
            calories = 500f,
            carbohydrates = 5f,
            sugar = 2f,
            totalFat = 40f,
            protein = 32f,
            saturatedFat = 9.2f,
            sodium = 1f
        )

        val disallowedItems = listOf(
            "bread", "pasta", "rice", "potato", "sugar", "high fructose corn syrup",
            "corn", "wheat", "flour", "legume", "bean", "soda", "milk", "skim milk",
            "honey", "agave", "cereal", "oat", "quinoa", "yogurt", "juice"
        )

        disallowedItems.forEachIndexed { index, item ->
            val meal = createMeal(
                id = index,
                nutrition = validNutrition,
                ingredients = listOf("chicken", item)
            )

            every { mealsDataSource.getAllMeals() } returns listOf(meal)

            val result = getKetoMealUseCase.suggestMeal()

            assertNull(result, "Meal with disallowed ingredient '$item' should return null")
        }
    }



}
