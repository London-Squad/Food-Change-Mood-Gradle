package presentation.ketoSuggestionHelper

import logic.ketoMealHelper.GetKetoMealUseCase
import model.Meal
import presentation.BaseView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader

class KetoSuggestionHelperView(
    private val getKetoMealUseCase: GetKetoMealUseCase,
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter,
    private val uiMealPrinter: UIMealPrinter
) : BaseView {
    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)

    override fun start() {
        getKetoMealUseCase.initSuggestedList()
        printHeader()
        printKetoMealSuggestion()
    }

    private fun printHeader() {
        uiMealPrinter.printHeader("🥑 Keto Meal Suggestion")
        uiMealPrinter.printTextWithinWidth("you can like the meal to see full detail, or dislike it to get another meal.")
    }

    tailrec private fun printKetoMealSuggestion() {

        val ketoMeal = getKetoMealUseCase.suggestMeal()

        if (ketoMeal == null) {
            printLn("\nNo more meals to suggest!")
            return
        }

        printMealDescription(ketoMeal)
        selectNextUI(ketoMeal)
    }

    private fun selectNextUI(meal: Meal){
        printLn("\nDo you like it? (y = like, n = dislike, x = exit): ")
        when (getValidInputFromUser()) {
            "y" -> uiMealPrinter.printMealDetails(meal)
            "n" -> printKetoMealSuggestion()
            "x" -> printLn("Returning to main menu...")
        }
    }

    private fun printMealDescription(meal: Meal) {
        printLn("\nTry this meal: ${meal.name}")
        uiMealPrinter.printTextWithinWidth("Description: ${meal.description}")
    }

    private fun getValidInputFromUser(): String =
        userInputReader.getValidUserInput(
            { it in INPUT_OPTIONS },
            "your choice: ",
            "invalid input"
        )

    private companion object {
        val INPUT_OPTIONS = listOf("y", "n", "x")
    }
}
