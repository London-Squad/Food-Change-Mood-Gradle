package presentation.suggestSweetWithoutEgg

import logic.suggestSweetWithoutEgg.SuggestSweetWithoutEggUseCase
import model.Meal
import presentation.BaseView
import presentation.utils.UserInputReader
import presentation.utils.ViewUtil

class SuggestSweetWithoutEggView(
    private val suggestSweetWithoutEggUseCase: SuggestSweetWithoutEggUseCase,
    private val viewUtil: ViewUtil,
    private val userInputReader: UserInputReader

) : BaseView {

    override fun start() {
        suggestSweetWithoutEggUseCase.initSuggestions()
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

        val sweetWithoutEgg = suggestSweetWithoutEggUseCase.suggestSweet()

        if (sweetWithoutEgg == null) {
            println("\nNo more meals to suggest!")
            return
        }

        printMealDescription(sweetWithoutEgg)
        println("\nDo you like it? (y = like, n = dislike, x = exit): ")
        when (getValidInputFromUser()) {
            "y" -> viewUtil.printMeal(sweetWithoutEgg)
            "n" -> printSweetSuggestion()
            "x" -> println("Returning to main menu...")
        }
    }

    private fun printMealDescription(meal: Meal) {
        println("\nTry this meal: ${meal.name}")
        viewUtil.printTextWithinWidth("Description: ${meal.description}")
    }

    private fun getValidInputFromUser(): String =
        userInputReader.getValidUserInput(
            { it in InputOptions },
            "your choice: ",
            "invalid input"
        )

    private companion object {
        val InputOptions = listOf("y", "n", "x")
    }
}