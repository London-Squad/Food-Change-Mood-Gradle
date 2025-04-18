package presentation

import logic.KetoMealHelper

class KetoSuggestionHelperView(private val ketoHelperUseCase:KetoMealHelper,
                               private val viewUtil: ViewUtil): BaseView{
    override fun start() {
        println("🥑 Keto Meal Suggestion")
        val suggestedMeal = ketoHelperUseCase.getKetoDishesSuggestion()
        viewUtil.printMeal(suggestedMeal)
    }

}
