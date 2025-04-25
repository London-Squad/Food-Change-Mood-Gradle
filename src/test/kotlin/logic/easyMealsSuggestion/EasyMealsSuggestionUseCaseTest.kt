package logic.easyMealsSuggestion

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import mealHelperTest.createMeal
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlin.test.assertEquals

class EasyMealsSuggestionUseCaseTest {

    private val dataSource = mockk<MealsDataSource>()
    private val useCase = EasyMealsSuggestionUseCase(dataSource)

    @Test
    fun `getRandomMeals should returns easy meals`() {
        val easy = createMeal(id = 1, minutes = 20, ingredients = listOf("1", "2", "3","4"), steps = listOf("1", "2", "3","4","5"))
        every { dataSource.getAllMeals() } returns listOf(easy)

        val result = useCase.getRandomMeals()

        assertThat(result).contains(easy)
    }

    @Test
    fun `getRandomMeals should excludes hard meals`() {
        val hard = createMeal(id = 2, minutes = 35, ingredients = listOf("1", "2", "3","4","5","6"), steps = listOf("1", "2", "3","4","5","6","7"))
        every { dataSource.getAllMeals() } returns listOf(hard)

        val result = useCase.getRandomMeals()

        assertThat(result).isEmpty()
    }

    @Test
    fun ` getRandomMeals should returns empty list when no easy meals`() {
        val hard1 = createMeal(id = 1, minutes = 50, ingredients = listOf("1", "2", "3","4","5","6","7","8"), steps = listOf("1", "2", "3","4","5","6","7"))
        val hard2 = createMeal(id = 2, minutes = 45, ingredients = listOf("1", "2", "3","4","5","6"), steps = listOf("1", "2", "3","4","5","6"))
        every { dataSource.getAllMeals() } returns listOf(hard1, hard2)

        val result = useCase.getRandomMeals()

        assertThat(result).isEmpty()
    }

    @Test
    fun `getRandomMeals should returns correct number of meals up to limit`() {
        val meals = List(20) { createMeal(id = it, minutes = 25, ingredients = listOf("1", "2", "3"), steps = listOf("1", "2", "3","4")) }
        every { dataSource.getAllMeals() } returns meals

        val result = useCase.getRandomMeals(10)

        assertEquals(10, result.size)
    }

    @Test
    fun `getRandomMeals should return meal with 30 minutes or less`() {
        val mealWith29Min=createMeal(id = 1, minutes = 29, ingredients = listOf("1", "2", "3"), steps = listOf("1", "2", "3","4"))
        val mealWith30Min=createMeal(id = 2, minutes = 30, ingredients = listOf("1", "2", "3"), steps = listOf("1", "2", "3","4"))
        val mealWith31Min=createMeal(id = 3, minutes = 31, ingredients = listOf("1", "2", "3"), steps = listOf("1", "2", "3","4"))
        every { dataSource.getAllMeals() } returns listOf(
            mealWith29Min,mealWith30Min,mealWith31Min
        )

        val result = useCase.getRandomMeals(10)

        assertThat(result).containsExactly(mealWith29Min, mealWith30Min)

    }

    @Test
    fun `getRandomMeals should ensures meals have no more than 5 ingredients`() {
        val mealWith5ingredients=createMeal(id = 1, minutes = 29, ingredients = listOf("1", "2", "3","4","5"), steps = listOf("1", "2", "3","4"))
        val mealWith3ingredients=createMeal(id = 2, minutes = 30, ingredients = listOf("1", "2", "3"), steps = listOf("1", "2", "3","4"))
        val mealWith6ingredients=createMeal(id = 3, minutes = 20, ingredients = listOf("1", "2", "3","4","5","6"), steps = listOf("1", "2", "3","4"))
        every { dataSource.getAllMeals() } returns listOf(
            mealWith5ingredients,mealWith3ingredients,mealWith6ingredients
        )

        val result = useCase.getRandomMeals(10)

        assertThat(result).containsExactly(mealWith5ingredients, mealWith3ingredients)
    }


    @Test
    fun `getRandomMeals should ensures meals have no more than 6 steps`() {
        val mealWith7steps=createMeal(id = 1, minutes = 29, ingredients = listOf("1", "2"), steps = listOf("1", "2", "3","4","5","6","7"))
        val mealWith6steps=createMeal(id = 2, minutes = 30, ingredients = listOf("1", "2"), steps = listOf("1", "2", "3","4","5","6"))
        val mealWith4steps=createMeal(id = 3, minutes = 20, ingredients = listOf("1", "2"), steps = listOf("1", "2", "3","4"))
        every { dataSource.getAllMeals() } returns listOf(
            mealWith7steps,mealWith6steps,mealWith4steps
        )
        val result = useCase.getRandomMeals(10)

        assertThat(result).containsExactly(mealWith6steps, mealWith4steps)
    }


    @Test
    fun `getRandomMeals should excludes meals with null minutes`() {
        val nullMinutes = createMeal(id = 1, minutes = null, ingredients = listOf("1", "2", "3"), steps = listOf("1", "2", "3"))
        val valid = createMeal(id = 2, minutes = 30, ingredients = listOf("1", "2", "3"), steps = listOf("1", "2", "3"))
        every { dataSource.getAllMeals() } returns listOf(nullMinutes, valid)

        val result = useCase.getRandomMeals()

        assertThat(result).containsExactly(valid )
    }


    @Test
    fun ` getRandomMeals should includes meals with exactly max limits`() {
        val exact = createMeal(id = 1, minutes = 30, ingredients = listOf("1", "2", "3","4","5"), steps = listOf("1", "2", "3","4","5","6"))
        every { dataSource.getAllMeals() } returns listOf(exact)

        val result = useCase.getRandomMeals()

        assertThat(result).containsExactly(exact)
    }


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

        assertThat(result).isEmpty()
    }
}
