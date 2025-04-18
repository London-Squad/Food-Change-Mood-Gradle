package presentation.ketoSuggestionHelper

import logic.ketoMealHelper.KetoMealHelper
import presentation.BaseView
import presentation.utils.ViewUtil

class KetoSuggestionHelperView(
    private val ketoHelperUseCase: KetoMealHelper,
    private val viewUtil: ViewUtil
) : BaseView {
    override fun start() {
        println("🥑 Keto Meal Suggestion")
        val suggestedMeal = ketoHelperUseCase.getKetoDishesSuggestion()

        if (suggestedMeal == null) {
            println("no meals found :'("); return
        }

        viewUtil.printMeal(suggestedMeal)
    }

}
