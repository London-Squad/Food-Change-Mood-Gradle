package presentation.iraqiMeals

import logic.getIraqiMeals.GetIraqiMealsUseCase
import presentation.BaseView
import presentation.utils.UIMealsListPrinter

class IraqiMealsView(
    private val uiMealsListPrinter: UIMealsListPrinter,
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase,
) : BaseView {

    override fun start() {
        val iraqiMeals = getIraqiMealsUseCase.getIraqiMeals()
        uiMealsListPrinter.printMeals(iraqiMeals, "Iraqi Meals")
    }
}