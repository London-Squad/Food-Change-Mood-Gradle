package presentation.suggestSweetWithoutEgg

import logic.suggestSweetWithoutEgg.SuggestSweetWithoutEggUseCase
import presentation.BaseView
import presentation.utils.UserInputReader
import presentation.utils.ViewUtil

class SuggestSweetWithoutEggView(
    private val suggestSweetWithoutEggUseCase: SuggestSweetWithoutEggUseCase,
    private val viewUtil: ViewUtil,
    private val userInputReader: UserInputReader

) : BaseView {

    override fun start() {

        suggestSweetWithoutEggUseCase.clearSuggestedList()

        printHeader()
        printSweetSuggestion()
    }

    private fun printHeader() {
        println("------------------------------------------")
        println("           Sweet Without Egg              ")
        println("------------------------------------------")
        println("you can like the meal to see full detail, or dislike it to get another meal.")
    }

    private fun printSweetSuggestion() {

        val sweet = suggestSweetWithoutEggUseCase.suggestSweet()

        if (sweet == null) {
            println("\nNo more sweets to suggest!")
            return
        }

        println("\nTry this sweet: ${sweet.name}")
        viewUtil.printTextWithinWidth("Description: ${sweet.description}")

        println("Do you like it? (y = like, n = dislike, x = exit): ")

        when (getValidInputFromUser()) {
            "y" -> viewUtil.printMeal(sweet)
            "n" -> printSweetSuggestion()
            "x" -> println("Returning to main menu...")
        }

    }

    private fun getValidInputFromUser(): String =
        userInputReader.getValidUserInput(
            {it in listOf("y", "n", "x")},
            "your choice: ",
            "invalid input"
        )
}