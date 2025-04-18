package logic

import model.Meal

class GetHealthyFastFoodMealsUseCase(private val mealRepository: MealsDataSource) {

    companion object {
        private const val MAX_PREPARATION_TIME = 15
    }

    fun getHealthyFastFoodMeals(): List<Meal> {
        val mealsWithValidNutritionAndTime = mealRepository.getAllMeals().filter {
            it.minutes != null &&
                    it.minutes <= MAX_PREPARATION_TIME &&
                    it.nutrition.totalFat != null &&
                    it.nutrition.saturatedFat != null &&
                    it.nutrition.carbohydrates != null
        }

        val maxTotalFat = mealsWithValidNutritionAndTime
            .mapNotNull { it.nutrition.totalFat }
            .average()
            .toFloat()

        val maxSaturatedFat = mealsWithValidNutritionAndTime
            .mapNotNull { it.nutrition.saturatedFat }
            .average()
            .toFloat()

        val maxCarbohydrates = mealsWithValidNutritionAndTime
            .mapNotNull { it.nutrition.carbohydrates }
            .average()
            .toFloat()

        return mealsWithValidNutritionAndTime.filter { meal ->
            isVeryLowFatCarbMeal(
                meal,
                maxTotalFat,
                maxSaturatedFat,
                maxCarbohydrates
            )
        }
    }

    private fun List<Meal>.calculateAverage(property: (Meal) -> Float?): Float {
        val values = this.mapNotNull(property)
        return if (values.isNotEmpty()) values.average().toFloat() else 0f
    }

    private fun isVeryLowFatCarbMeal(
        meal: Meal,
        maxTotalFat: Float,
        maxSaturatedFat: Float,
        maxCarbohydrates: Float
    ): Boolean {
        return meal.nutrition.totalFat != null &&
                meal.nutrition.saturatedFat != null &&
                meal.nutrition.carbohydrates != null &&
                meal.nutrition.totalFat <= maxTotalFat &&
                meal.nutrition.saturatedFat <= maxSaturatedFat &&
                meal.nutrition.carbohydrates <= maxCarbohydrates
    }
}
