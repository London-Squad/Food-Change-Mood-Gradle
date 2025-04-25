package logic.getHighCalorieMeals

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import model.Meal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class GetHighCalorieMealsUseCaseTest {
    private lateinit var getHighCalorieMealsUseCase: GetHighCalorieMealsUseCase
    private lateinit var mealsDataSource: MealsDataSource

    @BeforeEach
    fun setup() {
        mealsDataSource = mockk(relaxed = false)
        getHighCalorieMealsUseCase = GetHighCalorieMealsUseCase(mealsDataSource)
    }

    @Test
    fun `suggestMeal should return null when there are no meals with calories more than 700`() {
        every { mealsDataSource.getAllMeals() } returns FakeHighCaloriesMeals.allLowCaloriesMeals

        getHighCalorieMealsUseCase.initSuggestedList()
        getHighCalorieMealsUseCase.loadSuggestedMealsToMemory()
        val result = getHighCalorieMealsUseCase.suggestMeal()

        assertThat(result).isNull()
    }

    @Test
    fun `suggestMeal should return null when there are no meals are available`() {
        every { mealsDataSource.getAllMeals() } returns emptyList()

        getHighCalorieMealsUseCase.initSuggestedList()
        getHighCalorieMealsUseCase.loadSuggestedMealsToMemory()
        val result = getHighCalorieMealsUseCase.suggestMeal()

        assertThat(result).isNull()
    }

    @Test
    fun `suggestMeal should return null when calories are null`() {
        every { mealsDataSource.getAllMeals() } returns listOf(
            FakeHighCaloriesMeals.mealWithNullCalories
        )

        getHighCalorieMealsUseCase.initSuggestedList()
        getHighCalorieMealsUseCase.loadSuggestedMealsToMemory()
        val result = getHighCalorieMealsUseCase.suggestMeal()

        assertThat(result).isNull()
    }

    @Test
    fun `suggestMeal should return a random high calories meal`() {
        every { mealsDataSource.getAllMeals() } returns FakeHighCaloriesMeals.allMeals


        getHighCalorieMealsUseCase.initSuggestedList()
        getHighCalorieMealsUseCase.loadSuggestedMealsToMemory()
        val result = getHighCalorieMealsUseCase.suggestMeal()

        assertThat(result).isIn(FakeHighCaloriesMeals.allHighCaloriesMeals)
    }


    // TODO: CHECK WHATS WRONG WITH THESE
//    @Test
//    fun `suggestMeal should return a random high calories meal with no repetition`() {
//        every { mealsDataSource.getAllMeals() } returns FakeHighCaloriesMeals.allMeals
//
//        getHighCalorieMealsUseCase.initSuggestedList()
//        getHighCalorieMealsUseCase.loadSuggestedMealsToMemory()
//getHighCalorieMealsUseCase.suggestMeal()
//        val firstSuggestion = getHighCalorieMealsUseCase.suggestMeal()
//
//        assertThat(firstSuggestion).isNotNull()
//    }

//    @Test
//    fun `suggestMeal should return a null when there are no more non-suggested meals`() {
//        every { mealsDataSource.getAllMeals() } returns listOf(
//            FakeHighCaloriesMeals.mealWithHighCalories701,
//            FakeHighCaloriesMeals.mealWithHighCalories1200
//        )
//
//        getHighCalorieMealsUseCase.initSuggestedList()
//        getHighCalorieMealsUseCase.loadSuggestedMealsToMemory()
//        getHighCalorieMealsUseCase.suggestMeal()
//        getHighCalorieMealsUseCase.suggestMeal()
//        val result = getHighCalorieMealsUseCase.suggestMeal()
//
//        assertThat(result).isNull()
//    }

    @ParameterizedTest
    @MethodSource("highCaloriesMealsProvider")
    fun `suggestMeal should return meal with calories more than 700`(meals: List<Meal>) {
        every { mealsDataSource.getAllMeals() } returns meals
        getHighCalorieMealsUseCase.initSuggestedList()

        getHighCalorieMealsUseCase.loadSuggestedMealsToMemory()
        val result = getHighCalorieMealsUseCase.suggestMeal()

        assertThat(result).isNotNull()
    }

    private companion object {
        @JvmStatic
        fun highCaloriesMealsProvider(): List<List<Meal>> = listOf(
            listOf(FakeHighCaloriesMeals.mealWithHighCalories701),
            listOf(FakeHighCaloriesMeals.mealWithHighCalories840),
            listOf(FakeHighCaloriesMeals.mealWithHighCalories1200)
        )
    }

}
