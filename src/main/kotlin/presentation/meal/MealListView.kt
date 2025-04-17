package presentation.meal

import model.Meal
import presentation.BaseView

class MealListView(
    private val mealList: List<Meal>
) : BaseView {

    override fun start() {
        if (isEmptyList()) return

        while (true) {
            try {
                printAllMeals()
                val mealIndexFromUser = getMealIndexFromUser()
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

    private fun isEmptyList(): Boolean {
        println("there is no Meals to Display")
        return mealList.isEmpty()
    }

    private fun printAllMeals() {
        mealList.forEachIndexed(::printMeal)
    }

    private fun printMeal(index: Int, meal: Meal) {
        println("${index + 1}- ${meal.name}")
    }

    private fun getMealIndexFromUser(): Int {
        println()
        val size = mealList.size
        when (size) {
            1 -> println("enter 1 to display details")
            else -> println("choose a meal to display its details(1 - $size)")
        }
        print("your choice: ")
        val index = readlnOrNull()?.toInt()?.minus(1) ?: throw Exception("please Enter a valid input")
        if (index >= size || index < 0) throw Exception("please enter a valid number")
        println()
        return index
    }

    private fun printMealDetails(selectedMeal: Meal) {
        MealDetailsView(selectedMeal).apply { start() }
    }
}