package presentation.easyMeal

import io.mockk.mockk
import io.mockk.verify
import logic.easyMealsSuggestion.EasyMealsSuggestionUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.utils.UIMealsListPrinter

class EasyMealViewTest {

    private lateinit var easyMealView: EasyMealView
    private lateinit var easyMealsSuggestionUseCase: EasyMealsSuggestionUseCase
    private lateinit var uiMealsListPrinter: UIMealsListPrinter

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
}