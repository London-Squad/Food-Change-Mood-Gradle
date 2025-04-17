package presentation.easyMeal

import logic.useCase.EasyMealsSuggestionUseCase
import model.Meal
import presentation.BaseView
import presentation.meal.MealDetailsView
import presentation.meal.MealListView

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