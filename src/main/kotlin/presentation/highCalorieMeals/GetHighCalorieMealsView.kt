package presentation.highCalorieMeals

import logic.getHighCalorieMeals.GetHighCalorieMealsUseCase
import model.Meal
import presentation.BaseView
import presentation.utils.UserInputReader
import presentation.utils.ViewUtil

class GetHighCalorieMealsView(
    private val getHighCalorieMealsUseCase: GetHighCalorieMealsUseCase,
    private val viewUtil: ViewUtil,
    private val userInputReader: UserInputReader
) : BaseView {

    override fun start() {
        getHighCalorieMealsUseCase.initSuggestions()
        printHeader()
        printHighCalorieMealSuggestion()
    }

    private fun printHeader() {
        println("------------------------------------------")
        println("      High Calorie Meals (>700 kcal)      ")
        println("------------------------------------------")
        println("you can like the meal to see full detail, or dislike it to get another meal.")
    }

    private fun printHighCalorieMealSuggestion() {

        val highCalorieMeal = getHighCalorieMealsUseCase.suggestHighCalorieMeal()

        if (highCalorieMeal == null) {
            println("\nNo more meals to suggest!")
            return
        }

        printMealDescription(highCalorieMeal)
        println("\nDo you like it? (y = like, n = dislike, x = exit): ")
        when (getValidInputFromUser()) {
            "y" -> viewUtil.printMeal(highCalorieMeal)
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