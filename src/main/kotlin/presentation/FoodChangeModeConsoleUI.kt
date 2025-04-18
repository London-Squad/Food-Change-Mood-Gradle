package presentation
import presentation.easyMeal.EasyMealView

class FoodChangeModeConsoleUI(
    private val getIraqiMealsView: GetIraqiMealsView,
    private val mealGuessGameView: MealGuessGameView,
    private val getHealtyFastFoodMealsView: GetHealtyFastFoodMealsView,
    private val suggestSweetWithoutEggView: SuggestSweetWithoutEggView,
    private val easyMealView: EasyMealView,
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
            1->getHealtyFastFoodMealsView.start()
            2 -> suggestSweetWithoutEggView.start()
            3 -> getIraqiMealsView.start()
            4 -> startEasyMealView()
            5 -> mealGuessGameView.start()
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
        println("\n\n=== please enter one of the following numbers ===")
        println("2- Suggest Sweets Without Eggs")
        println("3- Get a List of Iraqi Meals")
        println("4- Get Easy Food Suggestion")
        println("5- Meal Guess Game")
        println("11- Ingredient Game")
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