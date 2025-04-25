package presentation.iraqiMeals

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.getIraqiMeals.GetIraqiMealsUseCase
import mealHelperTest.createMeal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.utils.UIMealsListPrinter

class IraqiMealsViewTest {

    private lateinit var iraqiMealsView: IraqiMealsView
    private lateinit var uiMealsListPrinter: UIMealsListPrinter
    private lateinit var getIraqiMealsUseCase: GetIraqiMealsUseCase

    private val meal1 = createMeal(
        id = 1,
        name = "kubba",
        tags = listOf("iraqi"),
        description = "A traditional Iraqi dish",
    )

    private val meal2 = createMeal(
        id = 2,
        name = "Biryani",
        tags = listOf("iraqi"),
        description = "A flavorful Iraqi biryani",
    )

    @BeforeEach
    fun setUp() {
        uiMealsListPrinter = mockk(relaxed = true)
        getIraqiMealsUseCase = mockk(relaxed = true)

        iraqiMealsView = IraqiMealsView(
            uiMealsListPrinter,
            getIraqiMealsUseCase
        )
    }

    @Test
    fun `start should call printMeals with empty list when use case returns no meals`() {
        // Given
        every { getIraqiMealsUseCase.getIraqiMeals() } returns emptyList()

        // When
        iraqiMealsView.start()

        // Then
        verify { getIraqiMealsUseCase.getIraqiMeals() }
        verify { uiMealsListPrinter.printMeals(emptyList(), "Iraqi Meals") }
    }

    @Test
    fun `start should call printMeals with meals when use case returns non-empty list`() {
        // Given
        val iraqiMeals = listOf(meal1, meal2)
        every { getIraqiMealsUseCase.getIraqiMeals() } returns iraqiMeals

        // When
        iraqiMealsView.start()

        // Then
        verify { getIraqiMealsUseCase.getIraqiMeals() }
        verify { uiMealsListPrinter.printMeals(iraqiMeals, "Iraqi Meals") }
    }
}