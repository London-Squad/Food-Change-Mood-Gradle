package presentation.healthyFastFoodMeals

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.getHealthyFastFoodMeals.GetHealthyFastFoodMealsUseCase
import mealHelperTest.createMeal
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.utils.CLIPrinter
import presentation.utils.UIMealsListPrinter

class GetHealthyFastFoodMealsViewTest {

    private lateinit var getHealthyFastFoodMealsView: GetHealthyFastFoodMealsView
    private lateinit var getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase
    private lateinit var uiMealsListPrinter: UIMealsListPrinter
    private lateinit var cliPrinter: CLIPrinter

    private val meal1 = createMeal(
        id = 1,
        name = "Grilled Chicken Salad",
        minutes = 10,
        nutrition = Nutrition(
            calories = 300f,
            totalFat = 10f,
            sugar = 5f,
            sodium = 400f,
            protein = 25f,
            saturatedFat = 2f,
            carbohydrates = 15f
        ),
    )

    private val meal2 = createMeal(
        id = 2,
        name = "Veggie Wrap",
        minutes = 15,
        nutrition = Nutrition(
            calories = 350f,
            totalFat = 12f,
            sugar = 3f,
            sodium = 500f,
            protein = 10f,
            saturatedFat = 3f,
            carbohydrates = 40f
        ),
    )

    @BeforeEach
    fun setUp() {
        getHealthyFastFoodMealsUseCase = mockk(relaxed = true)
        uiMealsListPrinter = mockk(relaxed = true)
        cliPrinter = mockk(relaxed = true)

        getHealthyFastFoodMealsView = GetHealthyFastFoodMealsView(
            getHealthyFastFoodMealsUseCase,
            uiMealsListPrinter,
            cliPrinter
        )
    }

    @Test
    fun `start should call getHealthyFastFoodMeals when use case returns empty list`() {
        // Given
        every { getHealthyFastFoodMealsUseCase.getHealthyFastFoodMeals() } returns emptyList()

        // When
        getHealthyFastFoodMealsView.start()

        // Then
        verify { getHealthyFastFoodMealsUseCase.getHealthyFastFoodMeals() }
    }

    @Test
    fun `start should print no meals found message when use case returns empty list`() {
        // Given
        every { getHealthyFastFoodMealsUseCase.getHealthyFastFoodMeals() } returns emptyList()

        // When
        getHealthyFastFoodMealsView.start()

        // Then
        verify { cliPrinter.cliPrintLn("no meals found :'(") }
    }

    @Test
    fun `start should call getHealthyFastFoodMeals when use case returns non-empty list`() {
        // Given
        val healthyMeals = listOf(meal1, meal2)
        every { getHealthyFastFoodMealsUseCase.getHealthyFastFoodMeals() } returns healthyMeals

        // When
        getHealthyFastFoodMealsView.start()

        // Then
        verify { getHealthyFastFoodMealsUseCase.getHealthyFastFoodMeals() }
    }

    @Test
    fun `start should display meals when use case returns non-empty list`() {
        // Given
        val healthyMeals = listOf(meal1, meal2)
        every { getHealthyFastFoodMealsUseCase.getHealthyFastFoodMeals() } returns healthyMeals

        // When
        getHealthyFastFoodMealsView.start()

        // Then
        verify {
            uiMealsListPrinter.printMeals(
                healthyMeals,
                "🍽️ Healthy Fast Food Meals (ready in 15 minutes or less)"
            )
        }
    }
}