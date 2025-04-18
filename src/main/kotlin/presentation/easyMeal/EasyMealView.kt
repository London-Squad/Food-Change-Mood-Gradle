package presentation.easyMeal

import logic.easyMealsSuggestion.EasyMealsSuggestionUseCase
import presentation.BaseView
import presentation.mealDetails.MealListView

class EasyMealView(
    useCase: EasyMealsSuggestionUseCase
) : BaseView {
    private val easyMealList = useCase.getRandomMeals()

    override fun start() {
        printHeader()
        printMeals()
    }

    private fun printHeader() {
        println("------------------------------------------")
        println("               Easy Meals                 ")
        println("------------------------------------------")
    }

    private fun printMeals() {
        MealListView(easyMealList).apply { start() }
    }
}