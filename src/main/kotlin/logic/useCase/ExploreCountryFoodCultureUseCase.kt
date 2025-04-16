package logic.useCase

import logic.MealsDataSource
import model.Meal

class ExploreCountryFoodCultureUseCase(
    private val dataSource: MealsDataSource
) {
    fun exploreFoodCulture(country: String) {
        //search in name, tags, and description
        dataSource.getAllMeals()
            .filter { isExist(it, country) }
    }

    private fun isExist(meal: Meal, country: String): Boolean {
        return isExistInName(meal.name, country)
                || isExistInDescription(meal.description, country)
                || isExistInTags(meal.tags, country)
    }

    private fun isExistInName(mealName: String, country: String) = country.equals(mealName, ignoreCase = true)

    private fun isExistInDescription(description: String, country: String): Boolean {
        return description.contains(country, ignoreCase = true)
    }

    private fun isExistInTags(mealTags: List<String>, country: String): Boolean {
        return mealTags.any { country.equals(it, ignoreCase = true) }
    }


}