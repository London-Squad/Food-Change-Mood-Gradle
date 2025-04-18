package logic.getIraqiMeals

import logic.MealsDataSource
import model.Meal

class GetIraqiMealsUseCase(
    private val mealsDataSource: MealsDataSource
) {
    fun getIraqiMeals(): List<Meal> =
        mealsDataSource
            .getAllMeals()
            .filter(::isIraqiMeals)

    private fun isIraqiMeals(meal: Meal): Boolean =
        meal.tags.contains(IRAQI_TAG) || meal.description.lowercase().contains(IRAQ_KEYWORD)

    private companion object {
        const val IRAQI_TAG = "iraqi"
        const val IRAQ_KEYWORD = "iraq"
    }
}