package logic.getHighCalorieMeals

import logic.MealsDataSource
import model.Meal

class GetHighCalorieMealsUseCase(
    private val mealsDataSource: MealsDataSource
) {
    fun getHighCalorieMeals(): List<Meal> =
        mealsDataSource
            .getAllMeals()
            .filter(::isHighCalorieMeal)

    private fun isHighCalorieMeal(meal: Meal): Boolean =
        meal.nutrition.calories?.let { it > 700f } ?: false
}