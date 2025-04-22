package presentation

import logic.MealSuggester
import model.Meal
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader

abstract class MealSuggesterView(
    private val mealSuggesterUseCase: MealSuggester,
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter,
    private val uiMealPrinter: UIMealPrinter
) : BaseView {

    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)

    protected abstract var title: String

    override fun start() {
        mealSuggesterUseCase.initSuggestedList()
        printTitleAndInstructions(title)
        printNewSuggestion()
    }

    tailrec private fun printNewSuggestion() {

        val suggestedMeal = mealSuggesterUseCase.suggestMeal()

        if (suggestedMeal == null) {
            printLn("\nNo more meals to suggest!")
            return
        }

        printMealDescription(suggestedMeal)
        selectNextUI(suggestedMeal)
    }

    private fun selectNextUI(meal: Meal) {
        printLn("\nDo you like it? (y = like, n = dislike, x = exit): ")
        when (getValidInputFromUser()) {
            "y" -> uiMealPrinter.printMealDetails(meal)
            "n" -> printNewSuggestion()
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

    private fun printTitleAndInstructions(title: String) {
        uiMealPrinter.printHeader(title)
        uiMealPrinter.printTextWithinWidth("you can like the meal to see full detail, or dislike it to get another meal.")
    }

    private companion object {
        val INPUT_OPTIONS = listOf("y", "n", "x")
    }
}