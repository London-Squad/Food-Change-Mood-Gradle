package presentation.highCalorieMeals

import logic.getHighCalorieMeals.GetHighCalorieMealsUseCase
import model.Meal
import presentation.BaseView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader

class HighCalorieMealsView(
    private val getHighCalorieMealsUseCase: GetHighCalorieMealsUseCase,
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter,
    private val uiMealPrinter: UIMealPrinter
) : BaseView {
    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)

    override fun start() {
        getHighCalorieMealsUseCase.initSuggestions()
        printHeader()
        printHighCalorieMealSuggestion()
    }

    private fun printHeader() {
        uiMealPrinter.printHeader("High Calorie Meals ( > 700 kcal )")
        uiMealPrinter.printTextWithinWidth("you can like the meal to see full detail, or dislike it to get another meal.")
    }

    private fun printHighCalorieMealSuggestion() {

        val highCalorieMeal = getHighCalorieMealsUseCase.suggestHighCalorieMeal()

        if (highCalorieMeal == null) {
            printLn("\nNo more meals to suggest!")
            return
        }

        printMealDescription(highCalorieMeal)
        printLn("\nDo you like it? (y = like, n = dislike, x = exit): ")
        when (getValidInputFromUser()) {
            "y" -> uiMealPrinter.printMealDetails(highCalorieMeal)
            "n" -> printHighCalorieMealSuggestion()
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