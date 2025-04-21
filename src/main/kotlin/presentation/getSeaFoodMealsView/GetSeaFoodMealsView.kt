package presentation.getSeaFoodMealsView

import logic.getSeaFoodMealsUseCase.GetSeaFoodMealsUseCase
import presentation.BaseView
import presentation.utils.UIMealsListPrinter

class GetSeaFoodMealsView(
    private val getSeaFoodMealsUseCase: GetSeaFoodMealsUseCase,
    private val uiMealsListPrinter: UIMealsListPrinter,
    ): BaseView {
    override fun start() {
        uiMealsListPrinter.printMeals(
            getSeaFoodMealsUseCase.getSeaFoodMealsSortedByProtein(),
            "Seafood Meals (Sorted by Protein)"
        ) { meal -> "${meal.name} | protein = ${meal.nutrition.protein}" }
    }
}