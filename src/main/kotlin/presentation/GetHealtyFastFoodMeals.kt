package presentation

import logic.GetHealthyFastFoodMealsUseCase

class GetHealtyFastFoodMealsView(private val getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase,
                                 private val viewUtil: ViewUtil): BaseView{
    override fun start() {
        launchHealthyFoodList()
    }
    private fun launchHealthyFoodList() {
        val healthyMeals = getHealthyFastFoodMealsUseCase.getHealthyFastFoodMeals()
        println("🍽️ Healthy Fast Food Meals (ready in 15 minutes or less):")
        healthyMeals.forEach { meal ->
            println("• Meal: ${meal.name} | Preparation Time: ${meal.minutes} minutes")
        }
    }
}