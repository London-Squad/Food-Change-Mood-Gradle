package logic.easyMealsSuggestion

import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import model.Meal
import model.Nutrition
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class EasyMealsSuggestionUseCaseTest {

    private val dataSource = mockk<MealsDataSource>()
    private val useCase = EasyMealsSuggestionUseCase(dataSource)

    private fun createMeal(
        id: Int = 1,
        minutes: Int?,
        ingredientsCount: Int,
        stepsCount: Int
    ): Meal {
        return Meal(
            id = id,
            name = "Meal $id",
            minutes = minutes,
            dateSubmitted = LocalDate.now(),
            tags = listOf("easy"),
            nutrition = Nutrition(100f, 10f, 5f, 200f, 7f, 2f, 30f),
            steps = List(stepsCount) { "Step $it" },
            description = "Test meal",
            ingredients = List(ingredientsCount) { "Ingredient $it" }
        )
    }

    @Test
    fun `returns only easy meals`() {
        val easy = createMeal(1, 20, 4, 5)
        val hard = createMeal(2, 35, 6, 7)
        every { dataSource.getAllMeals() } returns listOf(easy, hard)

        val result = useCase.getRandomMeals()

        assertTrue(result.contains(easy))
        assertFalse(result.contains(hard))
    }

    @Test
    fun `returns empty list when no easy meals`() {
        val hard1 = createMeal(1, 50, 8, 7)
        val hard2 = createMeal(2, 45, 6, 6)
        every { dataSource.getAllMeals() } returns listOf(hard1, hard2)

        val result = useCase.getRandomMeals()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `returns correct number of meals up to limit`() {
        val meals = List(20) { createMeal(it, 25, 3, 4) }
        every { dataSource.getAllMeals() } returns meals

        val result = useCase.getRandomMeals(10)

        assertEquals(10, result.size)
        result.forEach {
            assertTrue(it.minutes!! <= 30)
            assertTrue(it.ingredients.size <= 5)
            assertTrue(it.steps.size <= 6)
        }
    }

    @Test
    fun `excludes meals with null minutes`() {
        val nullMinutes = createMeal(1, null, 3, 3)
        val valid = createMeal(2, 30, 3, 3)
        every { dataSource.getAllMeals() } returns listOf(nullMinutes, valid)

        val result = useCase.getRandomMeals()

        assertEquals(1, result.size)
        assertTrue(result.contains(valid))
    }

    @Test
    fun `edge case - meal with exactly max limits is included`() {
        val exact = createMeal(1, 30, 5, 6)
        every { dataSource.getAllMeals() } returns listOf(exact)

        val result = useCase.getRandomMeals()

        assertEquals(1, result.size)
        assertTrue(result.contains(exact))
    }

    @Test
    fun `edge case - meal exceeding any one limit is excluded`() {
        val tooManyIngredients = createMeal(1, 25, 6, 4)
        val tooManySteps = createMeal(2, 25, 4, 7)
        val tooLongTime = createMeal(3, 40, 4, 4)
        every { dataSource.getAllMeals() } returns listOf(
            tooManyIngredients,
            tooManySteps,
            tooLongTime
        )

        val result = useCase.getRandomMeals()

        assertTrue(result.isEmpty())
    }
}
