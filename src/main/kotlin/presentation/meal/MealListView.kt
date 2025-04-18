package presentation.meal

import model.Meal
import presentation.BaseView

class MealListView(
    private val mealList: List<Meal>
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
                print("press any key to continue:")
                readln()
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

    private fun getValidOptionFromUser(max: Int): Int {
        print("your choice: ")
        val userInput = readln().trim()
        return if (userInput.toIntOrNull() in 0..max)
            userInput.toInt()
        else {
            println("invalid input")
            getValidOptionFromUser(max)
        }
    }

    private fun printMealDetails(selectedMeal: Meal) {
        MealDetailsView(selectedMeal).apply { start() }
    }
}