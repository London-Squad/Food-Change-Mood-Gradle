package presentation.easyMeal

import logic.easyMealsSuggestion.EasyMealsSuggestionUseCase
import presentation.BaseView
import presentation.utils.UIMealsListPrinter

class EasyMealView(
    private val easyMealsSuggestionUseCase: EasyMealsSuggestionUseCase,
    private val uiMealsListPrinter: UIMealsListPrinter
) : BaseView {

    override fun start() {
        uiMealsListPrinter.printMeals(
            easyMealsSuggestionUseCase.getRandomMeals(),
            "Easy Meals"
        )
    }
}