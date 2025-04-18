package presentation.ketoSuggestionHelper

import logic.ketoMealHelper.KetoMealHelper
import presentation.BaseView
import presentation.utils.ViewUtil

class KetoSuggestionHelperView(private val ketoHelperUseCase: KetoMealHelper,
                               private val viewUtil: ViewUtil
): BaseView {
    override fun start() {
        println("🥑 Keto Meal Suggestion")
        val suggestedMeal = ketoHelperUseCase.getKetoDishesSuggestion()
        viewUtil.printMeal(suggestedMeal)
    }

}
