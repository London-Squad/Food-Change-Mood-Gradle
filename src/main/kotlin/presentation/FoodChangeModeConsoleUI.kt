package presentation

class FoodChangeModeConsoleUI(
    private val getIraqiMealsView: GetIraqiMealsView,
    private val mealGuessGameView: MealGuessGameView,
    private val mealSearchByNameView: MealSearchByNameView,
    private val mealSearchByDateView: MealSearchByDateView
) : BaseView {

    override fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()
        when (input) {
            2 -> mealSearchByNameView.start()
            3 -> getIraqiMealsView.start()
            5 -> mealGuessGameView.start()
            8 -> mealSearchByDateView.start()
            0 -> return
            else -> println("Invalid Input")
        }
        presentFeatures()
    }

    private fun showWelcome() {
        println("Welcome to Food Change Mode app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===\n")
        println("2- Search Meals by Name")
        println("3- Get a List of Iraqi Meals")
        println("5- Meal Guess Game")
        println("8- Search Meals by Add Date")
        println("0- Exit")
        println("Here: ")
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}