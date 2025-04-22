package presentation.ketoSuggestionHelper

import logic.ketoMealHelper.GetKetoMealUseCase
import model.Meal
import presentation.BaseView
import presentation.MealSuggesterView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader

class KetoSuggestionHelperView(
    private val getKetoMealUseCase: GetKetoMealUseCase,
    userInputReader: UserInputReader,
    cliPrinter: CLIPrinter,
    uiMealPrinter: UIMealPrinter
) : MealSuggesterView(getKetoMealUseCase, userInputReader, cliPrinter, uiMealPrinter) {

    override fun start() {
        getKetoMealUseCase.initSuggestedList()
        printTitleAndInstructions("🥑 Keto Meal Suggestion")
        printNewSuggestion()
    }
}
