package logic.search.byName

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.MealsDataSource
import logic.search.LevenshteinSearch
import logic.search.SearchCache
import logic.search.TextSearchAlgorithm
import model.Meal
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class MealSearchByNameUseCaseImplTest {

    private lateinit var mealSearchByNameUseCaseImpl: MealSearchByNameUseCaseImpl
    private lateinit var mealsDataSource: MealsDataSource
    private lateinit var searchAlgorithm: TextSearchAlgorithm
    private lateinit var cache: SearchCache
    private lateinit var indexBuilder: MealNameInvertedIndexBuilder

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
        searchAlgorithm = LevenshteinSearch(maxDistance = 2)
        cache = mockk(relaxed = true)
        indexBuilder = mockk(relaxed = true)

        every { mealsDataSource.getAllMeals() } returns meals
        every { cache.get(any()) } returns null
        every { cache.put(any(), any()) } returns Unit
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
        val cachedMeals = listOf(meal2)
        every { cache.get("beef") } returns cachedMeals

        val result = mealSearchByNameUseCaseImpl.searchMeals("beef")

        assertThat(result).isEqualTo(cachedMeals)
        verify { cache.get("beef") }
    }

    @Test
    fun `searchMeals should return null  when keyword is not in cache`() {
        val cachedMeals = emptyList<Meal>()
        every { cache.get("beef") } returns cachedMeals

        val result = mealSearchByNameUseCaseImpl.searchMeals("beef")

        assertThat(result).isEqualTo(cachedMeals)
        verify { cache.get("beef") }
    }

    @Test
    fun `searchMeals should return matching meals when keyword matches indexed meals`() {
        val result = mealSearchByNameUseCaseImpl.searchMeals("chicken")

        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.get("chicken") }
        verify { cache.put("chicken", listOf(meal1)) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return matching meals when keyword has typo`() {
        val result = mealSearchByNameUseCaseImpl.searchMeals("chiken")

        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.put("chiken", listOf(meal1)) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return matching meals when multi-word keyword matches indexed meals`() {
        val result = mealSearchByNameUseCaseImpl.searchMeals("chicken curry")

        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.get("chicken curry") }
        verify { cache.put("chicken curry", listOf(meal1)) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return matching meals when multi-word keyword with typos matches indexed meals`() {
        val result = mealSearchByNameUseCaseImpl.searchMeals("chicken cury")

        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.get("chicken cury") }
        verify { cache.put("chicken cury", listOf(meal1)) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return empty list when keyword does not match indexed meals`() {
        val result = mealSearchByNameUseCaseImpl.searchMeals("pasta")

        assertThat(result).isEmpty()
        verify { cache.get("pasta") }
        verify { cache.put("pasta", emptyList()) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return matching meals when index is empty using full scan`() {
        every { indexBuilder.getIndex() } returns emptyMap()

        val result = mealSearchByNameUseCaseImpl.searchMeals("chicken")

        assertThat(result).isEqualTo(listOf(meal1))
        verify { cache.get("chicken") }
        verify { cache.put("chicken", listOf(meal1)) }
        verify { indexBuilder.getIndex() }
        verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `searchMeals should return empty list when index and meals do not match using full scan`() {
        every { indexBuilder.getIndex() } returns emptyMap()

        val result = mealSearchByNameUseCaseImpl.searchMeals("pasta")

        assertThat(result).isEmpty()
        verify { cache.get("pasta") }
        verify { cache.put("pasta", emptyList()) }
        verify { indexBuilder.getIndex() }
        verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `searchMeals should return empty list when keyword is empty`() {
        val result = mealSearchByNameUseCaseImpl.searchMeals("")

        assertThat(result).isEmpty()
        verify { cache.get("") }
        verify { cache.put("", emptyList()) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return empty list when meals data source is empty`() {
        every { mealsDataSource.getAllMeals() } returns emptyList()

        val result = mealSearchByNameUseCaseImpl.searchMeals("chicken")

        assertThat(result).isEmpty()
        verify { cache.get("chicken") }
        verify { cache.put("chicken", emptyList()) }
        verify { indexBuilder.getIndex() }
    }

    @Test
    fun `searchMeals should return cached meals when keyword case differs`() {
        val cachedMeals = listOf(meal1)
        every { cache.get("chicken") } returns cachedMeals

        val result = mealSearchByNameUseCaseImpl.searchMeals("Chicken")

        assertThat(result).isEqualTo(cachedMeals)
        verify { cache.get("Chicken") }
        verify { mealsDataSource.getAllMeals() }
    }

    @Test
    fun `searchMeals should exclude non-matching meals from indexed candidates`() {
        every { indexBuilder.getIndex() } returns mapOf(
            "pasta" to setOf(1)
        )

        val result = mealSearchByNameUseCaseImpl.searchMeals("pasta")

        assertThat(result).isEmpty()
        verify { cache.get("pasta") }
        verify { cache.put("pasta", emptyList()) }
        verify { indexBuilder.getIndex() }
        verify { mealsDataSource.getAllMeals() }
    }
}