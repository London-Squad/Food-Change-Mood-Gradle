package presentation.ketoSuggestionHelper

import logic.ketoMealHelper.KetoMealHelper
import presentation.BaseView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter

class KetoSuggestionHelperView(
    private val ketoHelperUseCase: KetoMealHelper,
    private val uiMealPrinter: UIMealPrinter,
    private val cliPrinter: CLIPrinter
) : BaseView {
    override fun start() {
        cliPrinter.cliPrintLn("🥑 Keto Meal Suggestion")
        val suggestedMeal = ketoHelperUseCase.getKetoDishesSuggestion()

        if (suggestedMeal == null) {
            cliPrinter.cliPrintLn("no meals found :'("); return
        }
        uiMealPrinter.printMealDetails(suggestedMeal)
    }
}
