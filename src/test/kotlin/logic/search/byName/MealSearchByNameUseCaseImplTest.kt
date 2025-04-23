package logic.search.byName

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.MealsDataSource
import logic.search.LevenshteinSearch
import logic.search.SearchCache
import logic.search.TestMealsProvider
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

    private val meal1 = TestMealsProvider.mealForNameSearch1
    private val meal2 = TestMealsProvider.mealForNameSearch2
    private val meals = TestMealsProvider.mealsForNameSearch

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
        val cachedMeals = listOf(meal2)
        every { cache.get("beef") } returns cachedMeals

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals("beef")

        // Then
        assertThat(result).isEqualTo(cachedMeals)
        verify { cache.get("beef") }
    }

    @Test
    fun `searchMeals should return null when keyword is not in cache`() {
        // Given
        val cachedMeals = emptyList<Meal>()
        every { cache.get("beef") } returns cachedMeals

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals("beef")

        // Then
        assertThat(result).isEqualTo(cachedMeals)
        verify { cache.get("beef") }
    }

    @Test
    fun `searchMeals should return matching meals when keyword matches indexed meals`() {
        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals("chicken")

        // Then
        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.get("chicken") }
        verify { cache.put("chicken", listOf(meal1)) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return matching meals when keyword has typo`() {
        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals("chiken")

        // Then
        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.put("chiken", listOf(meal1)) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return matching meals when multi-word keyword matches indexed meals`() {
        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals("chicken curry")

        // Then
        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.get("chicken curry") }
        verify { cache.put("chicken curry", listOf(meal1)) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return matching meals when multi-word keyword with typos matches indexed meals`() {
        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals("chicken cury")

        // Then
        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.get("chicken cury") }
        verify { cache.put("chicken cury", listOf(meal1)) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return empty list when keyword does not match indexed meals`() {
        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals("pasta")

        // Then
        assertThat(result).isEmpty()
        verify { cache.get("pasta") }
        verify { cache.put("pasta", emptyList()) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return matching meals when index is empty using full scan`() {
        // Given
        every { indexBuilder.getIndex() } returns emptyMap()

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals("chicken")

        // Then
        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.get("chicken") }
        verify { cache.put("chicken", listOf(meal1)) }
        verify { indexBuilder.getIndex() }
        verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `searchMeals should return empty list when index and meals do not match using full scan`() {
        // Given
        every { indexBuilder.getIndex() } returns emptyMap()

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals("pasta")

        // Then
        assertThat(result).isEmpty()
        verify { cache.get("pasta") }
        verify { cache.put("pasta", emptyList()) }
        verify { indexBuilder.getIndex() }
        verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `searchMeals should return empty list when keyword is empty`() {
        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals("")

        // Then
        assertThat(result).isEmpty()
        verify { cache.get("") }
        verify { cache.put("", emptyList()) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return empty list when meals data source is empty`() {
        // Given
        every { mealsDataSource.getAllMeals() } returns emptyList()

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals("chicken")

        // Then
        assertThat(result).isEmpty()
        verify { cache.get("chicken") }
        verify { cache.put("chicken", emptyList()) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return cached meals when keyword case differs`() {
        // Given
        val cachedMeals = listOf(meal1)
        every { cache.get("chicken") } returns cachedMeals

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals("Chicken")

        // Then
        assertThat(result).isEqualTo(cachedMeals)
        verify { cache.get("Chicken") }
        verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `searchMeals should exclude non-matching meals from indexed candidates`() {
        // Given
        every { indexBuilder.getIndex() } returns mapOf(
            "pasta" to setOf(1)
        )

        // When
        val result = mealSearchByNameUseCaseImpl.searchMeals("pasta")

        // Then
        assertThat(result).isEmpty()
        verify { cache.get("pasta") }
        verify { cache.put("pasta", emptyList()) }
        verify { indexBuilder.getIndex() }
        verify { mealsDataSource.getAllMeals() }
    }
}