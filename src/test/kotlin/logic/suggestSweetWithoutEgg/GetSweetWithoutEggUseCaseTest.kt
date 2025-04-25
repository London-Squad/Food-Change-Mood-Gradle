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
            name = "Bitter Dish",
            tags = listOf("bitter"),
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

    @Test
    fun `suggestMeal should return null when all meals contain egg or eggs in ingredients`() {
        val mealWithEgg = createMeal(
            name = "Dish with Egg",
            tags = listOf("sweet"),
            ingredients = listOf("flour", "egg")
        )
        val mealWithEggs = createMeal(
            name = "Dish with Eggs",
            tags = listOf("sweet"),
            ingredients = listOf("flour", "eggs")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(mealWithEgg, mealWithEggs)

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isNull()
    }

    @Test
    fun `suggestMeal should return a valid meal when no egg or eggs are in ingredients`() {
        val sweetEggFreeMeal = createMeal(
            name = "Egg-Free Dish",
            tags = listOf("sweet"),
            ingredients = listOf("flour", "sugar")
        )
        every { mealsDataSource.getAllMeals() } returns listOf(sweetEggFreeMeal)

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isEqualTo(sweetEggFreeMeal)
    }
}