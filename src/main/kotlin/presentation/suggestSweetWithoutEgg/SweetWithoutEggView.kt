package presentation.suggestSweetWithoutEgg

import logic.suggestSweetWithoutEgg.GetSweetWithoutEggUseCase

import presentation.MealSuggesterView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader

class SweetWithoutEggView(
    getSweetWithoutEggUseCase: GetSweetWithoutEggUseCase,
    userInputReader: UserInputReader,
    cliPrinter: CLIPrinter,
    uiMealPrinter: UIMealPrinter
) : MealSuggesterView(getSweetWithoutEggUseCase, userInputReader, cliPrinter, uiMealPrinter) {

    override var title: String = "Sweet Without Egg"
}