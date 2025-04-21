package presentation.ketoSuggestionHelper

import logic.ketoMealHelper.GetKetoMealUseCase
import model.Meal
import presentation.BaseView
import presentation.utils.UserInputReader
import presentation.utils.ViewUtil

class KetoSuggestionHelperView(
    private val ketoHelperUseCase: GetKetoMealUseCase,
    private val viewUtil: ViewUtil,
    private val userInputReader: UserInputReader
) : BaseView {

    override fun start() {
        ketoHelperUseCase.initSuggestions()
        printHeader()
        printHighCalorieMealSuggestion()
    }

    private fun printHeader() {
        println("------------------------------------------")
        println("    \uD83E\uDD51  Keto Meal Suggestion    ")
        println("------------------------------------------")
        println("you can like the meal to see full detail, or dislike it to get another meal.")
    }

    private fun printHighCalorieMealSuggestion() {

        val ketoMeal = ketoHelperUseCase.suggestKetoMeal()

        if (ketoMeal == null) {
            println("\nNo more meals to suggest!")
            return
        }

        printMealDescription(ketoMeal)
        println("\nDo you like it? (y = like, n = dislike, x = exit): ")
        when (getValidInputFromUser()) {
            "y" -> viewUtil.printMeal(ketoMeal)
            "n" -> printHighCalorieMealSuggestion()
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
