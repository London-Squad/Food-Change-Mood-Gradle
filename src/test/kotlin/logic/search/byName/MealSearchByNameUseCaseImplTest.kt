package logic.search.byName

import com.google.common.truth.Truth.assertThat
import mealHelperTest.createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.MealsDataSource
import logic.search.LevenshteinSearch
import logic.search.SearchCache
import logic.search.TextSearchAlgorithm
import model.Meal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MealSearchByNameUseCaseImplTest {

    private lateinit var mealSearchByNameUseCaseImpl: MealSearchByNameUseCaseImpl
    private lateinit var mealsDataSource: MealsDataSource
    private lateinit var searchAlgorithm: TextSearchAlgorithm
    private lateinit var cache: SearchCache
    private lateinit var indexBuilder: MealNameInvertedIndexBuilder

    private val meal1 = createMeal(
        id = 1,
        name = "Chicken Curry",
    )

    private val meal2 = createMeal(
        id = 2,
        name = "Beef Stew",
    )

    private val meals = listOf(meal1, meal2)

    @BeforeEach
    fun setUp() {
        mealsDataSource = mockk(relaxed = true)
        searchAlgorithm = LevenshteinSearch(maxDistance = 2)
        cache = mockk(relaxed = true)
        indexBuilder = mockk(relaxed = true)

        every { mealsDataSource.getAllMeals() } returns meals
        every { cache.get(any()) } returns null
        every { indexBuilder.getIndex() } returns mapOf(
            "chicken" to setOf(0),
            "curry" to setOf(0),
            "beef" to setOf(1),
            "stew" to setOf(1)
        )

        mealSearchByNameUseCaseImpl = MealSearchByNameUseCaseImpl(
            mealsDataSource,
            searchAlgorithm,
            cache,
            indexBuilder
        )
    }

    @Test
    fun `searchMeals should return cached meals when keyword is in cache`() {
        // Given
        val input = "beef"
        val cachedMeals = listOf(meal2)
        every { cache.get(input) } returns cachedMeals

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals(input)

        // Then
        assertThat(result).isEqualTo(cachedMeals)
        verify { cache.get(input) }
    }

    @Test
    fun `searchMeals should return null when keyword is not in cache`() {
        // Given
        val input = "beef"
        val cachedMeals = emptyList<Meal>()
        every { cache.get(input) } returns cachedMeals

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals(input)

        // Then
        assertThat(result).isEqualTo(cachedMeals)
        verify { cache.get(input) }
    }

    @Test
    fun `searchMeals should return matching meals when keyword matches indexed meals`() {
        // Given
        val input = "chicken"

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals(input)

        // Then
        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.get(input) }
        verify { cache.put(input, listOf(meal1)) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return matching meals when keyword has typo`() {
        // Given
        val input = "chiken"

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals(input)

        // Then
        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.put(input, listOf(meal1)) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return matching meals when multi-word keyword matches indexed meals`() {
        // Given
        val input = "chicken curry"

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals(input)

        // Then
        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.get(input) }
        verify { cache.put(input, listOf(meal1)) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return matching meals when multi-word keyword with typos matches indexed meals`() {
        // Given
        val input = "chicken cury"

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals(input)

        // Then
        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.get(input) }
        verify { cache.put(input, listOf(meal1)) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return empty list when keyword does not match indexed meals`() {
        // Given
        val input = "pasta"

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals(input)

        // Then
        assertThat(result).isEmpty()
        verify { cache.get(input) }
        verify { cache.put(input, emptyList()) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return matching meals when index is empty using full scan`() {
        // Given
        val input = "chicken"
        every { indexBuilder.getIndex() } returns emptyMap()

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals(input)

        // Then
        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.get(input) }
        verify { cache.put(input, listOf(meal1)) }
        verify { indexBuilder.getIndex() }
        verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `searchMeals should return empty list when index and meals do not match using full scan`() {
        // Given
        val input = "pasta"
        every { indexBuilder.getIndex() } returns emptyMap()

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals(input)

        // Then
        assertThat(result).isEmpty()
        verify { cache.get(input) }
        verify { cache.put(input, emptyList()) }
        verify { indexBuilder.getIndex() }
        verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `searchMeals should return empty list when keyword is empty`() {
        // Given
        val input = ""

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals(input)

        // Then
        assertThat(result).isEmpty()
        verify { cache.get(input) }
        verify { cache.put(input, emptyList()) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return empty list when meals data source is empty`() {
        // Given
        val input = "chicken"
        every { mealsDataSource.getAllMeals() } returns emptyList()

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals(input)

        // Then
        assertThat(result).isEmpty()
        verify { cache.get(input) }
        verify { cache.put(input, emptyList()) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return cached meals when keyword case differs`() {
        // Given
        val input = "Chicken"
        val cachedMeals = listOf(meal1)
        every { cache.get("chicken") } returns cachedMeals

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals(input)

        // Then
        assertThat(result).isEqualTo(cachedMeals)
        verify { cache.get(input) }
        verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `searchMeals should exclude non-matching meals from indexed candidates`() {
        // Given
        val input = "pasta"
        every { indexBuilder.getIndex() } returns mapOf(
            "pasta" to setOf(1)
        )

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals(input)

        // Then
        assertThat(result).isEmpty()
        verify { cache.get(input) }
        verify { cache.put(input, emptyList()) }
        verify { indexBuilder.getIndex() }
        verify { mealsDataSource.getAllMeals() }
    }
}