package logic

import model.Meal

class GetIraqiMealsUseCase(
    private val mealsDataSource: MealsDataSource
) {
    fun getIraqiMeals(): List<Meal> {
        val meals = mealsDataSource.getAllMeals()

        return meals.filter(::onlyIraqiMeals)
    }

    private fun onlyIraqiMeals(input: Meal): Boolean {
        return input.tags.contains(IRAQI_TAG)
                || input.description.lowercase().contains(IRAQ_KEYWORD)
    }

    companion object {
        const val IRAQI_TAG = "iraqi"
        const val IRAQ_KEYWORD = "iraq"
    }
}