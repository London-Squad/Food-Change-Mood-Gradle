package logic

import model.Meal

class GymHelperUseCase(
    private val mealsDataSource: MealsDataSource
) {
    fun getGymMembersMeals(
        desiredCalories: Float,
        desiredProtein: Float,
        approximatePercent: Double = 0.1
    ): List<Meal> {
        val minCalories = desiredCalories * (1 - approximatePercent).toFloat()
        val maxCalories = desiredCalories * (1 + approximatePercent).toFloat()
        val minProtein = desiredProtein * (1 - approximatePercent).toFloat()
        val maxProtein = desiredProtein * (1 + approximatePercent).toFloat()

        return mealsDataSource.getAllMeals()
            .filter { isHighQualityMeal(it) }
            .filter { meal ->
                meal.nutrition.calories!! in minCalories..maxCalories ||
                        meal.nutrition.protein!! in minProtein..maxProtein
            }
    }

    private fun isHighQualityMeal(meal: Meal): Boolean =
        meal.nutrition.protein != null && meal.nutrition.protein > 0f &&
                meal.nutrition.calories != null && meal.nutrition.calories > 0f
}