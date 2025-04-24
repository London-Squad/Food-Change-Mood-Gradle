package logic.getSeaFoodMealsUseCase

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import logic.MealsDataSource

import mealHelperTest.createMeal
import mealHelperTest.createNutrition
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetSeaFoodMealsUseCaseTest {

    private val mockMealsDataSource = mockk<MealsDataSource>()
    private val useCase = GetSeaFoodMealsUseCase(mockMealsDataSource)

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getSeaFoodMealsSortedByProtein should sort seafood meals by protein descending`() {
        // Given
        val meal1 = createMeal(name = "Salmon", tags = listOf("Seafood"),  nutrition = createNutrition(protein = 30f))
        val meal2 = createMeal(name = "Shrimp", tags = listOf("Seafood"), nutrition = createNutrition(protein = 25f))
        val meal3 = createMeal(name = "Tuna", tags = listOf("Seafood"),  nutrition = createNutrition(protein = 35f))

        every { mockMealsDataSource.getAllMeals() } returns listOf(meal1, meal2, meal3)

        // When
        val result = useCase.getSeaFoodMealsSortedByProtein()

        // Then
        assertEquals(listOf("Tuna", "Salmon", "Shrimp"), result.map { it.name })
        verify(exactly = 1) { mockMealsDataSource.getAllMeals() }
    }

    @Test
    fun `given meals with same protein when getSeaFoodMealsSortedByProtein then order remains stable`() {
        // Given
        val meal1 = createMeal(name = "Seafood Pasta A", tags = listOf("Seafood"),  nutrition = createNutrition(protein = 20f))
        val meal2 = createMeal(name = "Seafood Pasta B", tags = listOf("Seafood"),  nutrition = createNutrition(protein = 20f))

        every { mockMealsDataSource.getAllMeals() } returns listOf(meal1, meal2)

        // When
        val result = useCase.getSeaFoodMealsSortedByProtein()

        // Then
        assertEquals(listOf("Seafood Pasta A", "Seafood Pasta B"), result.map { it.name })
    }

    @Test
    fun `meals with null protein are ignored`() {
        // Given
        val meal1 = createMeal(name = "Valid Seafood", tags = listOf("Seafood"),  nutrition = createNutrition(protein = 15f))
        val meal2 = createMeal(name = "Null Protein", tags = listOf("Seafood"),  nutrition = createNutrition(protein = null))

        every { mockMealsDataSource.getAllMeals() } returns listOf(meal1, meal2)

        // When
        val result = useCase.getSeaFoodMealsSortedByProtein()

        // Then
        assertEquals(listOf("Valid Seafood"), result.map { it.name })
    }

    @Test
    fun `meals without Seafood tag are ignored`() {
        // Given
        val meal1 = createMeal(name = "Chicken Meal", tags = listOf("Chicken"), nutrition = createNutrition(protein = 10f))
        val meal2 = createMeal(name = "Fish Meal", tags = listOf("Seafood"),  nutrition = createNutrition(protein = 20f))

        every { mockMealsDataSource.getAllMeals() } returns listOf(meal1, meal2)

        // When
        val result = useCase.getSeaFoodMealsSortedByProtein()

        // Then
        assertEquals(listOf("Fish Meal"), result.map { it.name })
    }

    @Test
    fun `empty meals list returns empty result`() {
        // Given
        every { mockMealsDataSource.getAllMeals() } returns emptyList()

        // When
        val result = useCase.getSeaFoodMealsSortedByProtein()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `duplicate name or tag meals should not cause issues`() {
        // Given
        val meal1 = createMeal(name = "Fish", tags = listOf("Seafood"),  nutrition = createNutrition(protein = 10f))
        val meal2 = createMeal(name = "Fish", tags = listOf("Seafood"),  nutrition = createNutrition(protein = 15f))
        val meal3 = createMeal(name = "Octopus", tags = listOf("Seafood"),  nutrition = createNutrition(protein = 25f))

        every { mockMealsDataSource.getAllMeals() } returns listOf(meal1, meal2, meal3)
        //When

        val result = useCase.getSeaFoodMealsSortedByProtein()

        // Then
        assertEquals(listOf("Octopus", "Fish", "Fish"), result.map { it.name })
    }
}