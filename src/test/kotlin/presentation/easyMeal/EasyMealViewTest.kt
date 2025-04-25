package presentation.easyMeal

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.easyMealsSuggestion.EasyMealsSuggestionUseCase
import mealHelperTest.createMeal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.utils.UIMealsListPrinter

class EasyMealViewTest {

    private lateinit var easyMealView: EasyMealView
    private lateinit var easyMealsSuggestionUseCase: EasyMealsSuggestionUseCase
    private lateinit var uiMealsListPrinter: UIMealsListPrinter

    private val easyMeal1 = createMeal(
        minutes = 20,
        tags = listOf("easy"),
        ingredients = listOf("pasta", "tomato", "cheese"),
        steps = listOf("Step 1", "Step 2", "Step 3"),
    )

    private val easyMeal2 = createMeal(
        minutes = 10,
        tags = listOf("easy"),
        ingredients = listOf("bread", "cheese", "butter"),
        steps = listOf("Step 1", "Step 2"),
    )

    @BeforeEach
    fun setUp() {
        easyMealsSuggestionUseCase = mockk(relaxed = true)
        uiMealsListPrinter = mockk(relaxed = true)

        easyMealView = EasyMealView(
            easyMealsSuggestionUseCase,
            uiMealsListPrinter
        )
    }

    @Test
    fun `start should call getRandomMeals`() {
        // When
        easyMealView.start()

        // Then
        verify { easyMealsSuggestionUseCase.getRandomMeals() }
    }

    @Test
    fun `start should pass the meals list from the use case to the printMeals`() {
        // Given
        val easyMeals = listOf(easyMeal1, easyMeal2)
        every { easyMealsSuggestionUseCase.getRandomMeals() } returns easyMeals

        // When
        easyMealView.start()

        // Then
        verify { uiMealsListPrinter.printMeals(easyMeals, "Easy Meals") }
    }
}