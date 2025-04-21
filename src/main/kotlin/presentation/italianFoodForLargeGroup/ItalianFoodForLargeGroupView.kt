package presentation.italianFoodForLargeGroup

import logic.getItalianFoodForLargeGroup.GetItalianFoodForLargeGroupUseCase
import presentation.BaseView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealsListPrinter

class ItalianFoodForLargeGroupView(
    private val getItalianFoodForLargeGroupUseCase: GetItalianFoodForLargeGroupUseCase,
    private val uiMealsListPrinter: UIMealsListPrinter,
    private val cliPrinter: CLIPrinter
) : BaseView {
    override fun start() {
        val italianMeals = getItalianFoodForLargeGroupUseCase.getItalianMealsForLargeGroup()

        if (italianMeals.isEmpty()) {
            cliPrinter.cliPrintLn("no meals found :'("); return
        }

        uiMealsListPrinter.printMeals(italianMeals, "Iraqi Meals")

    }
}