package presentation

import presentation.easyMeal.EasyMealView

class FoodChangeModeConsoleUI(
    private val getIraqiMealsView: GetIraqiMealsView,
    private val iraqiMealsView: IraqiMealsView,
    private val suggestSweetWithoutEggView: SuggestSweetWithoutEggView,
    private val mealGuessGameView: MealGuessGameView,
    private val getHealthyFastFoodMealsView: GetHealthyFastFoodMealsView,
    private val easyMealView: EasyMealView,
    private val italianFoodForLargeGroupView: ItalianFoodForLargeGroupView,
    private val ketoSuggetionHelperView:KetoSuggetionHelperView,
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
            1->getHealthyFastFoodMealsView.start()
            2 -> suggestSweetWithoutEggView.start()
            3 -> iraqiMealsView.start()
            4 -> easyMealView.start()
            5 -> mealGuessGameView.start()
            7->ketoSuggetionHelperView.start()
            15 -> italianFoodForLargeGroupView.start()
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
        println("\n\n=== please enter one of the following numbers ===\n")
        println("1- Get a List of Healthy Fast Food Meals")
        println("2- Suggest Sweets Without Eggs")
        println("3- Get a List of Iraqi Meals")
        println("4- Get Easy Food Suggestion")
        println("5- Meal Guess Game")
        println("7- Get one Keto by Keto Helper")
        println("10- Explore other country culture")
        println("11- Ingredient Game")
        println("15- Get a List of Italian food that is suitable for large group of friends")
        println("0- Exit")
        println("here: ")
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}