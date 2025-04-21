package presentation.mealsContainPotato

import logic.getMealsContainPotato.GetMealsContainPotatoUseCase
import presentation.BaseView
import presentation.utils.UIMealsListPrinter

class GetMealsContainPotatoView(
    private val getMealsContainPotatoUseCase: GetMealsContainPotatoUseCase,
    private val uiMealsListPrinter: UIMealsListPrinter,
) : BaseView {

    override fun start() {
        uiMealsListPrinter.printMeals(
            getMealsContainPotatoUseCase.getRandomMeals(),
            "I Love Potato"
        )
    }
}