package presentation

import presentation.easyMeal.EasyMealView
import presentation.foodCulture.CountryFoodCultureView

class FoodChangeModeConsoleUI(
    private val suggestSweetWithoutEggView: SuggestSweetWithoutEggView,
    private val mealGuessGameView: MealGuessGameView,
    private val getHealthyFastFoodMealsView: GetHealthyFastFoodMealsView,
    private val easyMealView: EasyMealView,
    private val iraqiMealsView: GetIraqiMealsView,
    private val italianFoodForLargeGroupView: ItalianFoodForLargeGroupView,
    private val ketoSuggestionHelperView: KetoSuggestionHelperView,
    private val countryFoodCultureView: CountryFoodCultureView,
    private val ingredientGameView: IngredientGameView,
    private val gymHelperView: GymHelperView,
    private val getMealsContainPotatoView: GetMealsContainPotatoView

) : BaseView {

    override fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()
        when (input) {
            1 -> getHealthyFastFoodMealsView.start()
            2 -> suggestSweetWithoutEggView.start()
            3 -> iraqiMealsView.start()
            4 -> easyMealView.start()
            5 -> mealGuessGameView.start()
            7 -> ketoSuggestionHelperView.start()
            9 -> gymHelperView.start()
            10 -> countryFoodCultureView.start()
            11 -> ingredientGameView.start()
            12 -> getMealsContainPotatoView.start()
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

        println("\n\n=== please enter one of the following numbers ===")
        println("1- Get a List of Healthy Fast Food Meals")
        println("2- Suggest Sweets Without Eggs")
        println("3- Get a List of Iraqi Meals")
        println("4- Get Easy Food Suggestion")
        println("5- Meal Guess Game")
        println("7- Get one Keto by Keto Helper")
        println("9- Get a List of Gym Meals")
        println("10- Explore other country culture")
        println("11- Ingredient Game")
        println("12- I Love Potato")
        println("15- Get a List of Italian food that is suitable for large group of friends")
        println("0- Exit")
        println("here: ")
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}