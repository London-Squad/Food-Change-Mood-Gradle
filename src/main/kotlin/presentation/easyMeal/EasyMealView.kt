package presentation.easyMeal

import logic.useCase.EasyMealsSuggestionUseCase
import model.Meal

class EasyMealView(
    private val useCase: EasyMealsSuggestionUseCase
) {

    fun displayEasyMeals() {
        val easyMealList = useCase.getRandomMeals()
        printHeader()
        if (easyMealList.isEmpty()) {
            println("there is no Easy Meals")
            return
        }

        while (true) {
            try {
                easyMealList.forEachIndexed(::printMeal)
                getMealIndexFromUser(easyMealList.size).also { index ->
                    val selectedMeal = easyMealList[index]
                    navigateToMealDetails(selectedMeal)
                }
                return
            } catch (ex: Exception) {
                println(ex.message)
            }
        }
    }

    private fun printHeader() {
        println("------------------------------------------")
        println("               Easy Meals                 ")
        println("------------------------------------------")
    }

    private fun getMealIndexFromUser(size: Int): Int {
        println()
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

    private fun printMeal(index: Int, meal: Meal) {
        println("${index + 1}- ${meal.name}")
    }

    private fun navigateToMealDetails(selectedMeal: Meal) {
        EasyMealDetailsView(selectedMeal).apply {
            printMealDetails()
        }
    }
}