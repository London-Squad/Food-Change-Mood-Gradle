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

        val averageTotalFat = mealsWithValidNutritionAndTime
            .map { it.nutrition.totalFat!!.toDouble() }
            .average()
            .toFloat()

        val averageSaturatedFat = mealsWithValidNutritionAndTime
            .map { it.nutrition.saturatedFat!!.toDouble() }
            .average()
            .toFloat()

        val averageCarbohydrates = mealsWithValidNutritionAndTime
            .map { it.nutrition.carbohydrates!!.toDouble() }
            .average()
            .toFloat()

        return mealsWithValidNutritionAndTime.filter { meal ->
            isVeryLowFatCarbMeal(
                meal,
                averageTotalFat,
                averageSaturatedFat,
                averageCarbohydrates
            )
        }
    }

    private fun isVeryLowFatCarbMeal(
        meal: Meal,
        averageTotalFat: Float,
        averageSaturatedFat: Float,
        averageCarbohydrates: Float
    ): Boolean {
        return meal.nutrition.totalFat!! <= averageTotalFat &&
                meal.nutrition.saturatedFat!! <= averageSaturatedFat &&
                meal.nutrition.carbohydrates!! <= averageCarbohydrates
    }
}
