package presentation.highCalorieMeals

import logic.getHighCalorieMeals.GetHighCalorieMealsUseCase
import presentation.MealSuggesterView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader

class HighCalorieMealsView(
    private val getHighCalorieMealsUseCase: GetHighCalorieMealsUseCase,
    userInputReader: UserInputReader,
    cliPrinter: CLIPrinter,
    uiMealPrinter: UIMealPrinter
) : MealSuggesterView(getHighCalorieMealsUseCase, userInputReader, cliPrinter, uiMealPrinter) {

    override fun start() {
        getHighCalorieMealsUseCase.initSuggestedList()
        printHeader("High Calorie Meals ( > 700 kcal )")
        printNewSuggestion()
    }
}