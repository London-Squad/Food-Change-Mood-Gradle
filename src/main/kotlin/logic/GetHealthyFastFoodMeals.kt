package logic

import model.Meal

class GetHealthyFastFoodMeals(private val mealRepository: MealRepository) {
    companion object {
        private const val MAX_PREPARATION_TIME = 15
        private const val LOW_FAT_PERCENTAGE = 0.25f // 25%
    }

    fun getHealthyFastFoodMeals(): List<Meal> {
        val mealsWithLessPreparationTime = mealRepository.getAllMeals().
        filter { it.minutes <= MAX_PREPARATION_TIME }
        val quickMeals = mealsWithLessPreparationTime.filter { it.minutes <= MAX_PREPARATION_TIME }
        val maxTotalFat = mealsWithLessPreparationTime.calculateAverage { it.nutrition.totalFat } * LOW_FAT_PERCENTAGE
        val maxSaturatedFat = mealsWithLessPreparationTime.calculateAverage { it.nutrition.saturatedFat } * LOW_FAT_PERCENTAGE
        val maxCarbohydrates =mealsWithLessPreparationTime.calculateAverage { it.nutrition.carbohydrates }* LOW_FAT_PERCENTAGE
        return quickMeals.filter { meal ->
            isVeryLowFatCarbMeal(
                meal,
                maxTotalFat,
                maxSaturatedFat,
                maxCarbohydrates
            )
        }
    }

    private fun List<Meal>.calculateAverage(property: (Meal) -> Float): Float {
        return this.map(property).average().toFloat()
    }

    private fun isVeryLowFatCarbMeal(
        meal: Meal,
        maxTotalFat: Float,
        maxSaturatedFat: Float,
        maxCarbohydrates: Float
    ): Boolean {
        return meal.nutrition.totalFat <= maxTotalFat &&
                meal.nutrition.saturatedFat <= maxSaturatedFat &&
                meal.nutrition.carbohydrates <= maxCarbohydrates
    }
}