package presentation.italianFoodForLargeGroup

import logic.getItalianFoodForLargeGroup.GetItalianFoodForLargeGroupUseCase
import presentation.BaseView
import presentation.utils.UIMealsListPrinter

class ItalianFoodForLargeGroupView(
    private val getItalianFoodForLargeGroupUseCase: GetItalianFoodForLargeGroupUseCase,
    private val uiMealsListPrinter: UIMealsListPrinter,
) : BaseView {
    override fun start() {
        uiMealsListPrinter.printMeals(
            getItalianFoodForLargeGroupUseCase.getItalianMealsForLargeGroup(),
            "Italian Meals For Large Groups"
        )
    }
}