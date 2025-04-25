package logic.suggestSweetWithoutEgg

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import mealHelperTest.createMeal
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetSweetWithoutEggUseCaseTest {

    private lateinit var getSweetWithoutEggUseCase: GetSweetWithoutEggUseCase
    private lateinit var mealsDataSource: MealsDataSource

    @BeforeEach
    fun setup() {
        mealsDataSource = mockk(relaxed = true)
        getSweetWithoutEggUseCase = GetSweetWithoutEggUseCase(mealsDataSource)
    }

    @Test
    fun `isValidSuggestion should return true for sweet and egg-free meals`() {
        val meal = createMeal(
            name = "Sweet Egg-Free Cake",
            tags = listOf("sweet", "egg-free"),
            ingredients = listOf("flour", "sugar")
        )
        val result = getSweetWithoutEggUseCase.isValidSuggestion(meal)
        assertThat(result).isTrue()
    }

    @Test
    fun `isValidSuggestion should return false for sweet meals containing eggs`() {
        val meal = createMeal(
            name = "Sweet Cake with Egg",
            tags = listOf("sweet"),
            ingredients = listOf("flour", "sugar", "egg")
        )
        val result = getSweetWithoutEggUseCase.isValidSuggestion(meal)
        assertThat(result).isFalse()
    }

    @Test
    fun `isValidSuggestion should return false for non-sweet but egg-free meals`() {
        val meal = createMeal(
            name = "Savory Egg-Free Dish",
            tags = listOf("savory", "egg-free"),
            ingredients = listOf("flour", "salt")
        )
        val result = getSweetWithoutEggUseCase.isValidSuggestion(meal)
        assertThat(result).isFalse()
    }

    @Test
    fun `isValidSuggestion should return false for meals that are neither sweet nor egg-free`() {
        val meal = createMeal(
            name = "Savory Dish with Egg",
            tags = listOf("savory"),
            ingredients = listOf("flour", "egg")
        )
        val result = getSweetWithoutEggUseCase.isValidSuggestion(meal)
        assertThat(result).isFalse()
    }

    @Test
    fun `isValidSuggestion should handle case-insensitive tags and ingredients`() {
        val meal = createMeal(
            name = "Sweet Egg-Free Cake",
            tags = listOf("SweeT", "EGG-Free"),
            ingredients = listOf("Flour", "Sugar")
        )
        val result = getSweetWithoutEggUseCase.isValidSuggestion(meal)
        assertThat(result).isTrue()
    }

    @Test
    fun `isValidSuggestion should return false for meals containing egg in ingredients`() {
        val meal = createMeal(
            name = "Dish with Egg",
            tags = listOf("sweet"),
            ingredients = listOf("flour", "egg")
        )
        val result = getSweetWithoutEggUseCase.isValidSuggestion(meal)
        assertThat(result).isFalse()
    }

    @Test
    fun `isValidSuggestion should return false for meals containing eggs in ingredients`() {
        val meal = createMeal(
            name = "Dish with Eggs",
            tags = listOf("sweet"),
            ingredients = listOf("flour", "eggs")
        )
        val result = getSweetWithoutEggUseCase.isValidSuggestion(meal)
        assertThat(result).isFalse()
    }

    @Test
    fun `isValidSuggestion should return false for meals containing egg or eggs in a case-insensitive manner`() {
        val meal = createMeal(
            name = "Dish",
            tags = listOf("sweet"),
            ingredients = listOf("flour", "EGG")
        )
        val result = getSweetWithoutEggUseCase.isValidSuggestion(meal)
        assertThat(result).isFalse()

        val mealWithEggs = createMeal(
            name = "Dish",
            tags = listOf("sweet"),
            ingredients = listOf("flour", "EGGS")
        )
        val resultWithEggs = getSweetWithoutEggUseCase.isValidSuggestion(mealWithEggs)
        assertThat(resultWithEggs).isFalse()
    }

    @Test
    fun `isValidSuggestion should return true for meals without egg or eggs in ingredients`() {
        val meal = createMeal(
            name = "Egg-Free Dish",
            tags = listOf("sweet"),
            ingredients = listOf("flour", "sugar")
        )
        val result = getSweetWithoutEggUseCase.isValidSuggestion(meal)
        assertThat(result).isTrue()
    }

    @Test
    fun `suggestMeal should return a valid sweet and egg-free meal`() {
        val sweetEggFreeMeal = createMeal(
            name = "Sweet Egg-Free Cake",
            tags = listOf("sweet", "egg-free"),
            ingredients = listOf("flour", "sugar")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(sweetEggFreeMeal)

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isEqualTo(sweetEggFreeMeal)
    }

    @Test
    fun `suggestMeal should return null when no meals are available`() {
        every { mealsDataSource.getAllMeals() } returns emptyList()

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isNull()
    }

    @Test
    fun `suggestMeal should return null when no sweet and egg-free meals are available`() {
        val nonSweetMeal = createMeal(
            name = "Savory Dish",
            tags = listOf("savory"),
            ingredients = listOf("egg", "salt")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(nonSweetMeal)

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isNull()
    }

    @Test
    fun `suggestMeal should return null when all valid meals have been suggested`() {
        val sweetEggFreeMeal = createMeal(
            name = "Sweet Egg-Free Cake",
            tags = listOf("sweet", "egg-free"),
            ingredients = listOf("flour", "sugar")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(sweetEggFreeMeal)

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        getSweetWithoutEggUseCase.suggestMeal()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isNull()
    }

    @Test
    fun `suggestMeal should return one of the valid sweet and egg-free meals when multiple are available`() {
        val sweetEggFreeMeal1 = createMeal(
            name = "Sweet Egg-Free Cake",
            tags = listOf("sweet", "egg-free"),
            ingredients = listOf("flour", "sugar")
        )
        val sweetEggFreeMeal2 = createMeal(
            name = "Sweet Egg-Free Pie",
            tags = listOf("sweet", "egg-free"),
            ingredients = listOf("flour", "butter")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(sweetEggFreeMeal1, sweetEggFreeMeal2)

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isIn(listOf(sweetEggFreeMeal1, sweetEggFreeMeal2))
    }
}