package presentation

import presentation.easyMeal.EasyMealView
import presentation.foodCulture.CountryFoodCultureView

class FoodChangeModeConsoleUI(
    private val suggestSweetWithoutEggView: SuggestSweetWithoutEggView,
    private val getIraqiMealsView: GetIraqiMealsView,
    private val mealGuessGameView: MealGuessGameView,
    private val easyMealView: EasyMealView,
    private val countryFoodCultureView: CountryFoodCultureView
    private val ingredientGameView: IngredientGameView,
) : BaseView {

    override fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()
        when (input) {
            2 -> suggestSweetWithoutEggView.start()
            3 -> getIraqiMealsView.start()
            4 -> easyMealView.start()
            5 -> mealGuessGameView.start()
            10 -> countryFoodCultureView.start()
            11 -> ingredientGameView.start()
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

        println("\n\n=== please enter one of the following numbers ===")
        println("2- Suggest Sweets Without Eggs")
        println("3- Get a List of Iraqi Meals")
        println("4- Get Easy Food Suggestion")
        println("5- Meal Guess Game")
        println("10- Explore other country culture")
        println("11- Ingredient Game")
        println("0- Exit")
        println("here: ")
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}