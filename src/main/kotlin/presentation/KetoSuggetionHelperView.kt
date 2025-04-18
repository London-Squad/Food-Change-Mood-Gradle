package presentation

import logic.GetIraqiMealsUseCase
import logic.KetoMealHelper

class KetoSuggetionHelperView(private val ketoHelperUseCase:KetoMealHelper,
                              private val viewUtil: ViewUtil): BaseView{
    override fun start() {
        println("🥑 Keto Meal Suggestion")
        val suggestedMeal = ketoHelperUseCase.getKetoDishesSuggestion()
        viewUtil.printMeal(suggestedMeal)
    }

}
