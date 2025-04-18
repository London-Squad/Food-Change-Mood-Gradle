package presentation

import logic.GetHealthyFastFoodMealsUseCase

class GetHealthyFastFoodMealsView(
    private val getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase,
) : BaseView {
    override fun start() {
        val healthyMeals = getHealthyFastFoodMealsUseCase.getHealthyFastFoodMeals()
        println("🍽️ Healthy Fast Food Meals (ready in 15 minutes or less):")
        healthyMeals.forEach { meal ->
            println("• Meal: ${meal.name} | Preparation Time: ${meal.minutes} minutes")
        }
    }
}