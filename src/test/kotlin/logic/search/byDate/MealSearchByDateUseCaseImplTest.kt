package logic.search.byDate

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.MealsDataSource
import logic.util.InvalidDateFormatException
import logic.util.NoMealsFoundException
import model.Meal
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class MealSearchByDateUseCaseImplTest {

    private lateinit var mealSearchByDateUseCaseImpl: MealSearchByDateUseCaseImpl
    private lateinit var mealsDataSource: MealsDataSource
    private lateinit var dateIndexBuilder: MealDateInvertedIndexBuilder
    private lateinit var idIndexBuilder: IdIndexBuilder

    private val meal1 = Meal(
        id = 1,
        name = "Chicken Curry",
        minutes = 45,
        dateSubmitted = LocalDate.of(2023, 4, 16),
        tags = emptyList(),
        nutrition = Nutrition(
            calories = 600f,
            totalFat = 20f,
            sugar = 5f,
            sodium = 800f,
            protein = 30f,
            saturatedFat = 8f,
            carbohydrates = 50f
        ),
        steps = emptyList(),
        description = "Spicy",
        ingredients = emptyList()
    )
    private val meal2 = Meal(
        id = 2,
        name = "Beef Stew",
        minutes = 60,
        dateSubmitted = LocalDate.of(2023, 4, 16),
        tags = emptyList(),
        nutrition = Nutrition(
            calories = 700f,
            totalFat = 25f,
            sugar = 3f,
            sodium = 900f,
            protein = 40f,
            saturatedFat = 10f,
            carbohydrates = 30f
        ),
        steps = emptyList(),
        description = "Hearty",
        ingredients = emptyList()
    )
    private val meals = listOf(meal1, meal2)

    @BeforeEach
    fun setUp() {
        mealsDataSource = mockk(relaxed = true)
        dateIndexBuilder = mockk(relaxed = true)
        idIndexBuilder = mockk(relaxed = true)

        every { mealsDataSource.getAllMeals() } returns meals
        every { dateIndexBuilder.getIndex() } returns mapOf(
            LocalDate.of(2023, 4, 16) to listOf(0, 1)
        )
        every { idIndexBuilder.getIndex() } returns mapOf(
            1 to 0,
            2 to 1
        )

        mealSearchByDateUseCaseImpl = MealSearchByDateUseCaseImpl(
            mealsDataSource,
            dateIndexBuilder,
            idIndexBuilder
        )
    }

    @Test
    fun `searchMeals should return meal ID-name pairs for a valid date with matching meals`() {
        // Given
        val date = "2023-04-16"

        // When
        val result = mealSearchByDateUseCaseImpl.searchMeals(date)

        // Then
        assertThat(result).isEqualTo(listOf(1 to "Chicken Curry", 2 to "Beef Stew"))
        verify { dateIndexBuilder.getIndex() }
        verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `searchMeals should throw InvalidDateFormatException for an invalid date format`() {
        // Given
        val invalidDate = "invalid-date"

        // When & Then
        val exception = assertThrows<InvalidDateFormatException> {
            mealSearchByDateUseCaseImpl.searchMeals(invalidDate)
        }
        assertThat(exception.message).isEqualTo("Invalid date format: 'invalid-date'. Use yyyy-MM-dd (e.g., 2023-04-16).")
        verify(exactly = 0) { dateIndexBuilder.getIndex() }
        verify(exactly = 0) { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `searchMeals should throw NoMealsFoundException when date is not in index`() {
        // Given
        val date = "2023-04-17"

        // When & Then
        val exception = assertThrows<NoMealsFoundException> {
            mealSearchByDateUseCaseImpl.searchMeals(date)
        }
        assertThat(exception.message).isEqualTo("No meals found for date: 2023-04-17")
        verify { dateIndexBuilder.getIndex() }
        verify(exactly = 0) { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `searchMeals should return empty list when indices are invalid`() {
        // Given
        every { dateIndexBuilder.getIndex() } returns mapOf(
            LocalDate.of(2023, 4, 16) to listOf(2) // Index 2 is out of bounds
        )

        // When
        val result = mealSearchByDateUseCaseImpl.searchMeals("2023-04-16")

        // Then
        assertThat(result).isEmpty()
        verify { dateIndexBuilder.getIndex() }
        verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `getMealDetails should return meal for a valid ID`() {
        // Given
        val id = 1

        // When
        val result = mealSearchByDateUseCaseImpl.getMealDetails(id)

        // Then
        assertThat(result).isEqualTo(meal1)
        verify { idIndexBuilder.getIndex() }
        verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `getMealDetails should throw NoMealsFoundException when ID is not in index`() {
        // Given
        val id = 3

        // When & Then
        val exception = assertThrows<NoMealsFoundException> {
            mealSearchByDateUseCaseImpl.getMealDetails(id)
        }
        assertThat(exception.message).isEqualTo("No meal found with ID: 3")
        verify { idIndexBuilder.getIndex() }
        verify(exactly = 0) { mealsDataSource.getAllMeals() }
    }
}