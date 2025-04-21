package presentation.mealsContainPotato

import logic.getMealsContainPotato.GetMealsContainPotatoUseCase
import model.Meal
import presentation.BaseView
import presentation.utils.UserInputReader
import presentation.utils.ViewUtil

class GetMealsContainPotatoView(
    private val getMealsContainPotatoUseCase: GetMealsContainPotatoUseCase,
    private val viewUtil: ViewUtil,
    private val userInputReader: UserInputReader
) : BaseView {

    override fun start() {
        printHeader()
        val randomPotatoMeals = getMealsContainPotatoUseCase.getRandomMeals()

        if (randomPotatoMeals.isEmpty()) {
            println("There is no meals made from potato.")
            return
        }

        println("List of meals that contain potatoes:\n")
        printMealsNames(randomPotatoMeals)
        println("If you want the details of one of the meal, enter its number")
        println("If you want to go back to the main menu, enter 0")
        println()

        getValidOptionFromUser(randomPotatoMeals.size).takeIf { it !=0 }
            ?.also{ printMealAndWaitForEnter(randomPotatoMeals[it - 1])}

    }

    private fun printHeader() {
        println("------------------------------------------")
        println("              I Love Potato               ")
        println("------------------------------------------")
    }

    private fun printMealsNames(meals: List<Meal>) {

        meals.forEachIndexed { mealIndex, meal ->
            println("${mealIndex + 1}. ${meal.name}")
        }
        println("---------------------------------------------")
    }

    private fun printMealAndWaitForEnter(meal: Meal) {
        viewUtil.printMeal(meal)
        userInputReader.getUserInput("press Enter to go back to main menu")
    }

    private fun getValidOptionFromUser(max: Int): Int {
        print("your choice: ")
        val userInput = userInputReader.getUserInput().trim()
        return if (userInput.toIntOrNull() in 0..max)
            userInput.toInt()
        else {
            println("invalid input")
            getValidOptionFromUser(max)
        }
    }
}