package presentation

import presentation.easyMeal.EasyMealView
import presentation.foodCulture.CountryFoodCultureView

class FoodChangeModeConsoleUI(
    private val suggestSweetWithoutEggView: SuggestSweetWithoutEggView,
    private val getIraqiMealsView: GetIraqiMealsView,
    private val mealGuessGameView: MealGuessGameView,
    private val mealSearchByNameView: MealSearchByNameView,
    private val mealSearchByDateView: MealSearchByDateView,
    private val easyMealView: EasyMealView,
    private val countryFoodCultureView: CountryFoodCultureView,
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
            2 -> mealSearchByNameView.start()
            3 -> getIraqiMealsView.start()
            4 -> easyMealView.start()
            5 -> mealGuessGameView.start()
            6 -> suggestSweetWithoutEggView.start()
            8 -> mealSearchByDateView.start()
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
        println("\n\n=== Please enter one of the following numbers ===\n")
        println("2- Search Meals by Name")
        println("3- Get a List of Iraqi Meals")
        println("4- Get Easy Food Suggestion")
        println("5- Meal Guess Game")
        println("6- Suggest Sweets Without Eggs")
        println("8- Search Meals by Add Date")
        println("10- Explore other country culture")
        println("11- Ingredient Game")
        println("0- Exit")
        println("Here: ")
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}