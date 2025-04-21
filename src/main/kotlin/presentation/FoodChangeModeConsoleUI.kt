package presentation

import presentation.easyMeal.EasyMealView
import presentation.getSeaFoodMealsView.GetSeaFoodMealsView
import presentation.foodCulture.CountryFoodCultureView
import presentation.healthyFastFoodMeals.GetHealthyFastFoodMealsView
import presentation.gymHelper.GymHelperView
import presentation.highCalorieMeals.HighCalorieMealsView
import presentation.ingredientGame.IngredientGameView
import presentation.iraqiMeals.IraqiMealsView
import presentation.italianFoodForLargeGroup.ItalianFoodForLargeGroupView
import presentation.ketoSuggestionHelper.KetoSuggestionHelperView
import presentation.mealGuessGame.MealGuessGameView
import presentation.mealSearchByDate.MealSearchByDateView
import presentation.mealSearchByName.MealSearchByNameView
import presentation.mealsContainPotato.GetMealsContainPotatoView
import presentation.suggestSweetWithoutEgg.SweetWithoutEggView
import presentation.utils.CLIPrinter
import presentation.utils.UserInputReader

class FoodChangeModeConsoleUI(
    private val sweetWithoutEggView: SweetWithoutEggView,
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
    private val highCalorieMealsView: HighCalorieMealsView,
    private val getIraqiMealsUseCase: IraqiMealsView,
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter
) : BaseView {

    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)

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
            6 -> sweetWithoutEggView.start()
            7 -> ketoSuggestionHelperView.start()
            8 -> mealSearchByDateView.start()
            9 -> gymHelperView.start()
            10 -> countryFoodCultureView.start()
            11 -> ingredientGameView.start()
            12 -> getMealsContainPotatoView.start()
            13 -> highCalorieMealsView.start()
            14 -> getSeaFoodMealsView.start()
            15 -> italianFoodForLargeGroupView.start()
            0 -> return
            else -> cliPrinter.cliPrintLn("Invalid Input")
        }
        presentFeatures()
    }

    private fun showWelcome() {
        printLn("Welcome to Food Change Mode app")
    }

    private fun showOptions() {
        printLn("\n\n=== please enter one of the following numbers ===")
        printLn("1- Get a List of Healthy Fast Food Meals")
        printLn("2- Search Meals by Name")
        printLn("3- Get a List of Iraqi Meals")
        printLn("4- Get Easy Food Suggestion")
        printLn("5- Meal Guess Game")
        printLn("6- Suggest Sweets Without Eggs")
        printLn("7- Get one Keto by Keto Helper")
        printLn("8- Search Meals by Add Date")
        printLn("9- Get a List of Gym Meals")
        printLn("10- Explore other country culture")
        printLn("11- Ingredient Game")
        printLn("12- I Love Potato")
        printLn("13- Get High Calorie Meals (>700 kcal)")
        printLn("14- Get a List Of Sea Food Sorted By Protein")
        printLn("15- Get a List of Italian food that is suitable for large group of friends")
        printLn("0- Exit")
        cliPrinter.cliPrint("Here: ")
    }

    private fun getUserInput(): Int? {
        return userInputReader.getUserInput().toIntOrNull()
    }
}