package logic

import model.Meal

class GetHealthyFastFoodMealsUseCase(private val mealRepository: MealsDataSource) {
    companion object {
        private const val MAX_PREPARATION_TIME =15
    }

    fun getHealthyFastFoodMeals(): List<Meal> {
        val mealsWithLessPreparationTime = mealRepository.getAllMeals().
        filter { it.minutes  != null && it.minutes <= MAX_PREPARATION_TIME || it.tags.contains("15-minutes-or-less") }
        val maxTotalFat = mealsWithLessPreparationTime.calculateAverage { it.nutrition.totalFat?:0f}
        val maxSaturatedFat = mealsWithLessPreparationTime.calculateAverage { it.nutrition.saturatedFat?:0f }
        val maxCarbohydrates =mealsWithLessPreparationTime.calculateAverage { it.nutrition.carbohydrates?:0f }
        return mealsWithLessPreparationTime.filter { meal ->
            isVeryLowFatCarbMeal(
                meal,
                maxTotalFat,
                maxSaturatedFat,
                maxCarbohydrates
            )
        }
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
        return (meal.nutrition.totalFat ?: 0f) <= maxTotalFat &&
                (meal.nutrition.saturatedFat ?: 0f) <= maxSaturatedFat &&
                (meal.nutrition.carbohydrates ?: 0f) <= maxCarbohydrates
    }
