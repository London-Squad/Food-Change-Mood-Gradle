package presentation.suggestSweetWithoutEgg

import logic.suggestSweetWithoutEgg.GetSweetWithoutEggUseCase
import model.Meal
import presentation.BaseView
import presentation.utils.*

class SweetWithoutEggView(
    private val getSweetWithoutEggUseCase: GetSweetWithoutEggUseCase,
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter,
    private val uiMealPrinter: UIMealPrinter
) : BaseView {
    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)

    override fun start() {
        getSweetWithoutEggUseCase.initSuggestions()
        printHeader()
        printSweetSuggestion()
    }

    private fun printHeader() {
        uiMealPrinter.printHeader("Sweet Without Egg")
        uiMealPrinter.printTextWithinWidth("you can like the meal to see full detail, or dislike it to get another meal.")
    }

    private fun printSweetSuggestion() {

        val sweetWithoutEgg = getSweetWithoutEggUseCase.suggestSweet()

        if (sweetWithoutEgg == null) {
            printLn("\nNo more meals to suggest!")
            return
        }

        printMealDescription(sweetWithoutEgg)
        printLn("\nDo you like it? (y = like, n = dislike, x = exit): ")
        when (getValidInputFromUser()) {
            "y" -> uiMealPrinter.printMealDetails(sweetWithoutEgg)
            "n" -> printSweetSuggestion()
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