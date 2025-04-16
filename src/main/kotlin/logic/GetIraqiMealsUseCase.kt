package logic

import model.Meal

class GetIraqiMealsUseCase(
    private val mealsDataSource: MealsDataSource
) {
    fun getIraqiMeals(): List<Meal> = mealsDataSource.getAllMeals().filter(::onlyIraqiMeals)


    private fun onlyIraqiMeals(meal: Meal): Boolean {
        return meal.tags.contains(IRAQI_TAG)
                || meal.description.lowercase().contains(IRAQ_KEYWORD)
    }

    companion object {
        const val IRAQI_TAG = "iraqi"
        const val IRAQ_KEYWORD = "iraq"
    }
}