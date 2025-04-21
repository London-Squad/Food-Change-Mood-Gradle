package presentation.healthyFastFoodMeals

import logic.getHealthyFastFoodMeals.GetHealthyFastFoodMealsUseCase
import presentation.BaseView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealsListPrinter

class GetHealthyFastFoodMealsView(
    private val getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase,
    private val uiMealsListPrinter: UIMealsListPrinter,
    private val cliPrinter: CLIPrinter
) : BaseView {
    override fun start() {
        val healthyMeals = getHealthyFastFoodMealsUseCase.getHealthyFastFoodMeals()

        if (healthyMeals.isEmpty()) {cliPrinter.cliPrintLn("no meals found :'("); return}

        uiMealsListPrinter.printMeals(
            healthyMeals,
            "🍽️ Healthy Fast Food Meals (ready in 15 minutes or less)"
        )
    }
}