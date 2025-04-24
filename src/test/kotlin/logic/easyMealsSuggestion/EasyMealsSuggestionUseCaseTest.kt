package logic.easyMealsSuggestion

import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import createMeal
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlin.test.assertEquals

class EasyMealsSuggestionUseCaseTest {

    private val dataSource = mockk<MealsDataSource>()
    private val useCase = EasyMealsSuggestionUseCase(dataSource)

    // Test to return only easy meals
    @Test
    fun `getRandomMeals should returns easy meals`() {
        val easy = createMeal(id = 1, minutes = 20, ingredients = listOf("1", "2", "3","4"), steps = listOf("1", "2", "3","4","5"))
        every { dataSource.getAllMeals() } returns listOf(easy)

        val result = useCase.getRandomMeals()

        assertTrue(result.contains(easy))  // Assert that the easy meal is included
    }

    // Test to exclude hard meals
    @Test
    fun `getRandomMeals should excludes hard meals`() {
        val hard = createMeal(id = 2, minutes = 35, ingredients = listOf("1", "2", "3","4","5","6"), steps = listOf("1", "2", "3","4","5","6","7"))
        every { dataSource.getAllMeals() } returns listOf(hard)

        val result = useCase.getRandomMeals()

        assertFalse(result.contains(hard))  // Assert that the hard meal is excluded
    }

    // Test to return an empty list when no easy meals
    @Test
    fun ` getRandomMeals should returns empty list when no easy meals`() {
        val hard1 = createMeal(id = 1, minutes = 50, ingredients = listOf("1", "2", "3","4","5","6","7","8"), steps = listOf("1", "2", "3","4","5","6","7"))
        val hard2 = createMeal(id = 2, minutes = 45, ingredients = listOf("1", "2", "3","4","5","6"), steps = listOf("1", "2", "3","4","5","6"))
        every { dataSource.getAllMeals() } returns listOf(hard1, hard2)

        val result = useCase.getRandomMeals()

        assertTrue(result.isEmpty())  // Assert that the result is an empty list
    }

    // Test to return the correct number of meals
    @Test
    fun `getRandomMeals should returns correct number of meals up to limit`() {
        val meals = List(20) { createMeal(id = it, minutes = 25, ingredients = listOf("1", "2", "3"), steps = listOf("1", "2", "3","4")) }
        every { dataSource.getAllMeals() } returns meals

        val result = useCase.getRandomMeals(10)

        assertEquals(10, result.size)  // Assert the correct number of meals
    }

    // Test to ensure meals are within the time limit
    @Test
    fun `getRandomMeals should ensures meals are within the time limit`() {
        val meals = List(20) { createMeal(id = it, minutes = 25, ingredients = listOf("1", "2", "3"), steps = listOf("1", "2", "3","4")) }
        every { dataSource.getAllMeals() } returns meals

        val result = useCase.getRandomMeals(10)

        result.forEach {
            assertTrue(it.minutes!! <= 30)  // Assert that each meal has preparation time <= 30
        }
    }

    // Test to ensure meals have no more than 5 ingredients
    @Test
    fun `getRandomMeals should ensures meals have no more than 5 ingredients`() {
        val meals = List(20) { createMeal(id = it, minutes = 25, ingredients = listOf("1", "2", "3"), steps= listOf("1", "2", "3","4")) }
        every { dataSource.getAllMeals() } returns meals

        val result = useCase.getRandomMeals(10)

        result.forEach {
            assertTrue(it.ingredients.size <= 5)  // Assert that each meal has <= 5 ingredients
        }
    }

    // Test to ensure meals have no more than 6 steps
    @Test
    fun `getRandomMeals should ensures meals have no more than 6 steps`() {
        val meals = List(20) { createMeal(id = it, minutes = 25, ingredients = listOf("1", "2", "3"), steps = listOf("1", "2", "3","4")) }
        every { dataSource.getAllMeals() } returns meals

        val result = useCase.getRandomMeals(10)

        result.forEach {
            assertTrue(it.steps.size <= 6)  // Assert that each meal has <= 6 steps
        }
    }

    // Test to exclude meals with null minutes
    @Test
    fun `getRandomMeals should excludes meals with null minutes`() {
        val nullMinutes = createMeal(id = 1, minutes = null, ingredients = listOf("1", "2", "3"), steps = listOf("1", "2", "3"))
        val valid = createMeal(id = 2, minutes = 30, ingredients = listOf("1", "2", "3"), steps = listOf("1", "2", "3"))
        every { dataSource.getAllMeals() } returns listOf(nullMinutes, valid)

        val result = useCase.getRandomMeals()

        assertEquals(1, result.size)  // Assert that only one valid meal is returned
    }

    // Test to include meals with exactly max limits
    @Test
    fun ` getRandomMeals should includes meals with exactly max limits`() {
        val exact = createMeal(id = 1, minutes = 30, ingredients = listOf("1", "2", "3","4","5"), steps = listOf("1", "2", "3","4","5","6"))
        every { dataSource.getAllMeals() } returns listOf(exact)

        val result = useCase.getRandomMeals()

        assertTrue(result.contains(exact))  // Assert that the meal with exact max limits is included
    }

    // Test to exclude meals exceeding any one limit
    @Test
    fun `getRandomMeals should excludes meals exceeding any one limit`() {
        val tooManyIngredients = createMeal(id = 1, minutes = 25, ingredients = listOf("1", "2", "3","4","5","6"), steps = listOf("1", "2", "3","4"))
        val tooManySteps = createMeal(id = 2, minutes = 25, ingredients = listOf("1", "2", "3","4"), steps = listOf("1", "2", "3","4","5","6","7"))
        val tooLongTime = createMeal(id = 3, minutes = 40, ingredients = listOf("1", "2", "3","4"), steps = listOf("1", "2", "3","4"))
        every { dataSource.getAllMeals() } returns listOf(
            tooManyIngredients,
            tooManySteps,
            tooLongTime
        )

        val result = useCase.getRandomMeals()

        assertTrue(result.isEmpty())  // Assert that no meals are returned
    }
}
