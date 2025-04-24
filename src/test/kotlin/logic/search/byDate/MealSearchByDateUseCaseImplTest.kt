package logic.search.byDate

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.MealsDataSource
import logic.util.InvalidDateFormatException
import logic.util.NoMealsFoundException
import mealHelperTest.createMeal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class MealSearchByDateUseCaseImplTest {

    private lateinit var mealSearchByDateUseCaseImpl: MealSearchByDateUseCaseImpl
    private lateinit var mealsDataSource: MealsDataSource
    private lateinit var dateIndexBuilder: MealDateInvertedIndexBuilder
    private lateinit var idIndexBuilder: IdIndexBuilder

    private val mealA = createMeal(
        id = 101,
        name = "arriba   baked winter squash mexican style",
        dateSubmitted = LocalDate.of(2023, 5, 1),
    )

    private val mealB = createMeal(
        id = 102,
        name = "Lamb Chops",
        dateSubmitted = LocalDate.of(2023, 5, 1),
    )

    private val mealC = createMeal(
        id = 103,
        name = "Fish Tacos",
        dateSubmitted = LocalDate.of(2023, 6, 1),
    )

    private val meals = listOf(mealA, mealB, mealC)

    @BeforeEach
    fun setUp() {
        mealsDataSource = mockk(relaxed = true)
        dateIndexBuilder = mockk(relaxed = true)
        idIndexBuilder = mockk(relaxed = true)

        every { mealsDataSource.getAllMeals() } returns meals
        every { dateIndexBuilder.getIndex() } returns mapOf(
            mealA.dateSubmitted!! to listOf(0, 1), // 2023-05-01
            mealC.dateSubmitted!! to listOf(2)     // 2023-06-01
        )
        every { idIndexBuilder.getIndex() } returns mapOf(
            mealA.id to 0,
            mealB.id to 1,
            mealC.id to 2
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
        val date = "2023-05-01"

        // When
        val result = mealSearchByDateUseCaseImpl.searchMeals(date)

        // Then
        assertThat(result).isEqualTo(listOf(mealA.id to mealA.name, mealB.id to mealB.name))
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
        val date = "2023-07-01"

        // When & Then
        val exception = assertThrows<NoMealsFoundException> {
            mealSearchByDateUseCaseImpl.searchMeals(date)
        }
        assertThat(exception.message).isEqualTo("No meals found for date: 2023-07-01")
         verify { dateIndexBuilder.getIndex() }
         verify(exactly = 0) { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `searchMeals should return empty list when indices are invalid`() {
        // Given
        every { dateIndexBuilder.getIndex() } returns mapOf(
            mealA.dateSubmitted!! to listOf(3) // Index 3 is out of bounds
        )

        // When
        val result = mealSearchByDateUseCaseImpl.searchMeals("2023-05-01")

        // Then
        assertThat(result).isEmpty()
         verify { dateIndexBuilder.getIndex() }
         verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `getMealDetails should return meal for a valid ID`() {
        // Given
        val id = mealA.id

        // When
        val result = mealSearchByDateUseCaseImpl.getMealDetails(id)

        // Then
        assertThat(result).isEqualTo(mealA)
         verify { idIndexBuilder.getIndex() }
         verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `getMealDetails should throw NoMealsFoundException when ID is not in index`() {
        // Given
        val id = 104

        // When & Then
        val exception = assertThrows<NoMealsFoundException> {
            mealSearchByDateUseCaseImpl.getMealDetails(id)
        }
        assertThat(exception.message).isEqualTo("No meal found with ID: 104")
         verify { idIndexBuilder.getIndex() }
         verify(exactly = 0) { mealsDataSource.getAllMeals() }
    }
}