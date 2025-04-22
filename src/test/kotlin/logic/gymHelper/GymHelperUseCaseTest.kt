import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import logic.gymHelper.GymHelperUseCase
import model.Meal
import model.Nutrition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GymHelperUseCaseTest {

    private lateinit var dataSource: MealsDataSource
    private lateinit var useCase: GymHelperUseCase

    @BeforeEach
    fun setup() {
        dataSource = mockk()
        useCase = GymHelperUseCase(dataSource)
    }

    @Test
    fun `returns meals within desired range`() {
        every { dataSource.getAllMeals() } returns listOf(
            createMeal("Meal A", 500f, 40f),
            createMeal("Meal B", 800f, 20f) // out of protein range
        )

        val result = useCase.getGymMembersMeals(500f, 40f)

        assertEquals(1, result.size)
        assertEquals("Meal A", result[0].name)
    }

    @Test
    fun `returns empty list when no meals match`() {
        every { dataSource.getAllMeals() } returns listOf(
            createMeal("Meal A", 900f, 15f) // both out of range
        )

        val result = useCase.getGymMembersMeals(500f, 40f)

        assertEquals(0, result.size)
    }

    @Test
    fun `ignores meals with null nutrition values`() {
        every { dataSource.getAllMeals() } returns listOf(
            createMeal("Meal A", null, 40f),
            createMeal("Meal B", 500f, null)
        )

        val result = useCase.getGymMembersMeals(500f, 40f)

        assertEquals(0, result.size)
    }

    @Test
    fun `includes meal at the edge of acceptable range`() {
        val approx = useCase.approximatePercent()
        val caloriesEdge = 500f * (1 + approx).toFloat()
        val proteinEdge = 40f * (1 + approx).toFloat()

        every { dataSource.getAllMeals() } returns listOf(
            createMeal("Edge Meal", caloriesEdge, proteinEdge)
        )

        val result = useCase.getGymMembersMeals(500f, 40f)

        assertEquals(1, result.size)
        assertEquals("Edge Meal", result[0].name)
    }

    // Helper to quickly create a Meal with key values
    private fun createMeal(name: String, calories: Float?, protein: Float?): Meal {
        return Meal(
            id = name.hashCode(),
            name = name,
            minutes = 30,
            dateSubmitted = LocalDate.now(),
            tags = listOf("test"),
            nutrition = Nutrition(
                calories = calories,
                totalFat = 5f,
                sugar = 2f,
                sodium = 500f,
                protein = protein,
                saturatedFat = 2f,
                carbohydrates = 30f
            ),
            steps = listOf("Step 1", "Step 2"),
            description = "Testing meal",
            ingredients = listOf("ingredient1", "ingredient2")
        )
    }
}
