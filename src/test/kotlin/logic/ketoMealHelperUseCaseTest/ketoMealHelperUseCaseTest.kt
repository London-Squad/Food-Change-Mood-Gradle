package logic.ketoMealHelperUseCaseTest

import io.mockk.every
import io.mockk.mockk
import logic.MealSuggester
import logic.MealsDataSource
import logic.ketoMealHelper.GetKetoMealUseCase
import logic.ketoMealHelperUseCaseTest.fakeData.FackDataMeals
import mealHelperTest.createMeal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetKetoMealUseCaseTest {

    private lateinit var getKetoMealUseCase: GetKetoMealUseCase
    private lateinit var mealsDataSource: MealsDataSource
    private lateinit var mealSuggester: MealSuggester

    @BeforeEach
    fun setup() {
        mealsDataSource = mockk(relaxed = true)
        getKetoMealUseCase = GetKetoMealUseCase(mealsDataSource)
        mealSuggester = getKetoMealUseCase
    }

    @Test
    fun `suggestMeal returns null when no meals available`() {
        every { mealsDataSource.getAllMeals() } returns emptyList()
        getKetoMealUseCase.loadSuggestedMealsToMemory()

        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal returns valid keto meal when all nutrition values meet the requirements`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FackDataMeals.validMealWithValidNutrition)
        getKetoMealUseCase.loadSuggestedMealsToMemory()
        val result = getKetoMealUseCase.suggestMeal()

        assertEquals(FackDataMeals.validMealWithValidNutrition, result)
    }

    //hasDisallowedIngredient
    @Test
    fun `suggestMeal returns null for all meals with disallowed ingredients`() {
        val meals = FackDataMeals.disallowedIngredients.mapIndexed { index, ingredient ->
            createMeal(
                id = index, nutrition = FackDataMeals.validNutrition, ingredients = listOf("chicken", ingredient)
            )
        }

        every { mealsDataSource.getAllMeals() } returns meals
        getKetoMealUseCase.loadSuggestedMealsToMemory()

        val result = getKetoMealUseCase.suggestMeal()
        assertNull(result)
    }

    //--------------------------- isPassesKetoNutritionCheck
    @Test
    fun `suggestMeal returns null when calories is null`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FackDataMeals.mealWithCaloriesNull)
        getKetoMealUseCase.loadSuggestedMealsToMemory()

        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal returns null when carbohydrates is null`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FackDataMeals.mealWithCarbohydratesNull)
        getKetoMealUseCase.loadSuggestedMealsToMemory()

        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal returns null when carbohydrates exceed max allowed`() {

        every { mealsDataSource.getAllMeals() } returns listOf(FackDataMeals.mealWithCarbohydratesThatExceedMaxAllowed)
        getKetoMealUseCase.loadSuggestedMealsToMemory()

        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal returns null when sugar is null`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FackDataMeals.mealWithNullSugar)
        getKetoMealUseCase.loadSuggestedMealsToMemory()

        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal returns null when sugar exceeds max allowed`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FackDataMeals.mealWithHighSugar)
        getKetoMealUseCase.loadSuggestedMealsToMemory()

        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal returns null when total fat is null`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FackDataMeals.mealWithNullTotalFat)
        getKetoMealUseCase.loadSuggestedMealsToMemory()

        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal returns null when protein is null`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FackDataMeals.mealWithNullProtein)
        getKetoMealUseCase.loadSuggestedMealsToMemory()

        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal returns null when protein is too low`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FackDataMeals.mealWithLowProtein)
        getKetoMealUseCase.loadSuggestedMealsToMemory()

        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal returns null when saturated fat is null`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FackDataMeals.mealWithNullSaturatedFat)
        getKetoMealUseCase.loadSuggestedMealsToMemory()

        assertNull(getKetoMealUseCase.suggestMeal())
    }

    @Test
    fun `suggestMeal returns null when saturated fat is outside allowed range`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FackDataMeals.mealWithInvalidSaturatedFat)
        getKetoMealUseCase.loadSuggestedMealsToMemory()

        assertNull(getKetoMealUseCase.suggestMeal())
    }

    // here condition meal test in class Meal suggester
    @Test
    fun `suggestMeal returns null when candidateMeals is empty`() {
        every { mealsDataSource.getAllMeals() } returns emptyList()
        mealSuggester.loadSuggestedMealsToMemory()
        assertNull(mealSuggester.suggestMeal())
    }


}

