package presentation

import presentation.easyMeal.EasyMealView

class FoodChangeModeConsoleUI(
    private val iraqiMealsView: IraqiMealsView,
    private val mealGuessGameView: MealGuessGameView,
    private val easyMealView: EasyMealView,
    private val italianFoodForLargeGroupView: ItalianFoodForLargeGroupView,
) : BaseView {

    override fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()
        when (input) {
            3 -> iraqiMealsView.start()
            4 -> startEasyMealView()
            5 -> mealGuessGameView.start()
            15 -> italianFoodForLargeGroupView.start()
            0 -> return
            else -> println("Invalid Input")
        }
        presentFeatures()
    }

    private fun showWelcome() {
        println("Welcome to Food Change Mode app")
    }

    private fun showOptions() {
        //TODO: add option for new features

        println("\n\n=== please enter one of the following numbers ===\n")
        println("3- Get a List of Iraqi Meals")
        println("4- Get Easy Food Suggestion")
        println("5- Meal Guess Game")
        println("15- Get a List of Italian food that is suitable for large group of friends")
        println("0- Exit")
        println("here: ")
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }

    private fun startEasyMealView() {
        easyMealView.displayEasyMeals()
    }
}