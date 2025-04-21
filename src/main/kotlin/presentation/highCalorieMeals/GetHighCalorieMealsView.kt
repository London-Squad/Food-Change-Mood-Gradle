package presentation.highCalorieMeals

import logic.getHighCalorieMeals.GetHighCalorieMealsUseCase
import presentation.BaseView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealsListPrinter

class GetHighCalorieMealsView(
    private val getHighCalorieMealsUseCase: GetHighCalorieMealsUseCase,
    private val uiMealsListPrinter: UIMealsListPrinter,
    private val cliPrinter: CLIPrinter
) : BaseView {

    override fun start() {
        val highCalorieMeals = getHighCalorieMealsUseCase.getHighCalorieMeals()

        if (highCalorieMeals.isEmpty()) {
            cliPrinter.cliPrintLn("no meals found :'("); return
        }

        uiMealsListPrinter.printMeals(
            highCalorieMeals,
            "High Calorie Meals ( > 700 )",
        )
    }
}