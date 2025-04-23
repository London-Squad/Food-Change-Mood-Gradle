package presentation.ketoSuggestionHelper

import logic.ketoMealHelper.GetKetoMealUseCase

import presentation.MealSuggesterView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader

class KetoSuggestionHelperView(
    getKetoMealUseCase: GetKetoMealUseCase,
    userInputReader: UserInputReader,
    cliPrinter: CLIPrinter,
    uiMealPrinter: UIMealPrinter
) : MealSuggesterView(getKetoMealUseCase, userInputReader, cliPrinter, uiMealPrinter) {

    override var title: String = "🥑 Keto Meal Suggestion"
}
