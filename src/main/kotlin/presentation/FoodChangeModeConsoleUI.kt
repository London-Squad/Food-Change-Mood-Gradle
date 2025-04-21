package presentation

import presentation.easyMeal.EasyMealView
import presentation.getSeaFoodMealsView.GetSeaFoodMealsView
import presentation.foodCulture.CountryFoodCultureView
import presentation.healthyFastFoodMeals.GetHealthyFastFoodMealsView
import presentation.gymHelper.GymHelperView
import presentation.highCalorieMeals.GetHighCalorieMealsView
import presentation.ingredientGame.IngredientGameView
import presentation.iraqiMeals.IraqiMealsView
import presentation.italianFoodForLargeGroup.ItalianFoodForLargeGroupView
import presentation.ketoSuggestionHelper.KetoSuggestionHelperView
import presentation.mealGuessGame.MealGuessGameView
import presentation.mealSearchByDate.MealSearchByDateView
import presentation.mealSearchByName.MealSearchByNameView
import presentation.mealsContainPotato.GetMealsContainPotatoView
import presentation.suggestSweetWithoutEgg.SuggestSweetWithoutEggView
import presentation.utils.UserInputReader

class FoodChangeModeConsoleUI(
    private val suggestSweetWithoutEggView: SuggestSweetWithoutEggView,
    private val mealGuessGameView: MealGuessGameView,
    private val getHealthyFastFoodMealsView: GetHealthyFastFoodMealsView,
    private val mealSearchByNameView: MealSearchByNameView,
    private val mealSearchByDateView: MealSearchByDateView,
    private val easyMealView: EasyMealView,
    private val italianFoodForLargeGroupView: ItalianFoodForLargeGroupView,
    private val ketoSuggestionHelperView: KetoSuggestionHelperView,
    private val countryFoodCultureView: CountryFoodCultureView,
    private val ingredientGameView: IngredientGameView,
    private val gymHelperView: GymHelperView,
    private val getSeaFoodMealsView: GetSeaFoodMealsView,
    private val getMealsContainPotatoView: GetMealsContainPotatoView,
    private val getHighCalorieMealsView: GetHighCalorieMealsView,
    private val getIraqiMealsUseCase: IraqiMealsView,
    private val userInputReader: UserInputReader
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
            2 -> mealSearchByNameView.start()
            3 -> getIraqiMealsUseCase.start()
            4 -> easyMealView.start()
            5 -> mealGuessGameView.start()
            6 -> suggestSweetWithoutEggView.start()
            7 -> ketoSuggestionHelperView.start()
            8 -> mealSearchByDateView.start()
            9 -> gymHelperView.start()
            10 -> countryFoodCultureView.start()
            11 -> ingredientGameView.start()
            12 -> getMealsContainPotatoView.start()
            13 -> getHighCalorieMealsView.start()
            14 -> getSeaFoodMealsView.start()
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
        println("2- Search Meals by Name")
        println("3- Get a List of Iraqi Meals")
        println("4- Get Easy Food Suggestion")
        println("5- Meal Guess Game")
        println("6- Suggest Sweets Without Eggs")
        println("8- Search Meals by Add Date")
        println("7- Get one Keto by Keto Helper")
        println("9- Get a List of Gym Meals")
        println("10- Explore other country culture")
        println("11- Ingredient Game")
        println("12- I Love Potato")
        println("13- Get High Calorie Meals (>700 kcal)")
        println("14- Get a List Of Sea Food Sorted By Protein")
        println("15- Get a List of Italian food that is suitable for large group of friends")
        println("0- Exit")
        print("Here: ")
    }

    private fun getUserInput(): Int? {
        return userInputReader.getUserInput().toIntOrNull()
    }
}