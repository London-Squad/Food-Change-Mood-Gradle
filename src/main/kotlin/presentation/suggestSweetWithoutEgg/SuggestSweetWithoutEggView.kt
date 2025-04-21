package presentation.suggestSweetWithoutEgg

import logic.suggestSweetWithoutEgg.SuggestSweetWithoutEggUseCase
import presentation.BaseView
import presentation.utils.*

class SuggestSweetWithoutEggView(
    private val suggestSweetWithoutEggUseCase: SuggestSweetWithoutEggUseCase,
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter,
    private val uiMealPrinter: UIMealPrinter
) : BaseView {
    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)

    override fun start() {

        suggestSweetWithoutEggUseCase.clearSuggestedList()

        printHeader()
        printSweetSuggestion()
    }

    private fun printHeader() {
        printLn("------------------------------------------")
        printLn("           Sweet Without Egg              ")
        printLn("------------------------------------------")
        printLn("you can like the meal to see full detail, or dislike it to get another meal.")
    }

    private fun printSweetSuggestion() {

        val sweet = suggestSweetWithoutEggUseCase.suggestSweet()

        if (sweet == null) {
            printLn("\nNo more sweets to suggest!")
            return
        }

        printLn("\nTry this sweet: ${sweet.name}")
        uiMealPrinter.printTextWithinWidth("Description: ${sweet.description}")

        printLn("Do you like it? (y = like, n = dislike, x = exit): ")

        when (getValidInputFromUser()) {
            "y" -> uiMealPrinter.printMealDetails(sweet)
            "n" -> printSweetSuggestion()
            "x" -> printLn("Returning to main menu...")
        }

    }

    private fun getValidInputFromUser(): String =
        userInputReader.getValidUserInput(
            {it in listOf("y", "n", "x")},
            "your choice: ",
            "invalid input"
        )
}