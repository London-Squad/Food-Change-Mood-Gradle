package presentation.suggestSweetWithoutEgg

import logic.suggestSweetWithoutEgg.GetSweetWithoutEggUseCase
import presentation.MealSuggesterView
import presentation.utils.*

class SweetWithoutEggView(
    private val getSweetWithoutEggUseCase: GetSweetWithoutEggUseCase,
    userInputReader: UserInputReader,
    cliPrinter: CLIPrinter,
    uiMealPrinter: UIMealPrinter
) : MealSuggesterView(getSweetWithoutEggUseCase, userInputReader, cliPrinter, uiMealPrinter) {

    override fun start() {
        getSweetWithoutEggUseCase.initSuggestedList()
        printHeader("Sweet Without Egg")
        printNewSuggestion()
    }
}