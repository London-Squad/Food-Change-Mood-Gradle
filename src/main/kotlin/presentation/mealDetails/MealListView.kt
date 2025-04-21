package presentation.mealDetails

import model.Meal
import presentation.BaseView
import presentation.utils.UserInputReader

class MealListView(
    private val mealList: List<Meal>,
    private val userInputReader: UserInputReader
) : BaseView {

    override fun start() {
        if (mealList.isEmpty()) {
            println("there is no Meals to Display")
            return
        }

        while (true) {
            try {
                printAllMeals()
                val mealIndexFromUser = getValidOptionFromUser(mealList.size)
                mealList[mealIndexFromUser].apply { printMealDetails(this) }
                userInputReader.getUserInput("press any key to continue: ")
                return
            } catch (ex: Exception) {
                println(ex.message)
                println()
            }
        }
    }

    private fun printAllMeals() {
        mealList.forEachIndexed(::printMeal)
    }

    private fun printMeal(index: Int, meal: Meal) {
        println("${index + 1}- ${meal.name}")
    }

    private fun getValidOptionFromUser(max: Int): Int =
        userInputReader.getValidUserInput(
            { userInput -> userInput.toIntOrNull() in 0..max },
            "Your Choice: ",
            "invalid input"
        ).toInt()

    private fun printMealDetails(selectedMeal: Meal) {
        MealDetailsView(selectedMeal).apply { start() }
    }
}