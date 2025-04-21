package presentation.easyMeal

import logic.easyMealsSuggestion.EasyMealsSuggestionUseCase
import presentation.BaseView
import presentation.mealDetails.MealListView
import presentation.utils.UserInputReader

class EasyMealView(
    useCase: EasyMealsSuggestionUseCase,
    private val userInputReader: UserInputReader
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
        MealListView(easyMealList, userInputReader).apply { start() }
    }
}