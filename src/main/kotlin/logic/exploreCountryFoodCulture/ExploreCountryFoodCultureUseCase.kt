package logic.exploreCountryFoodCulture

import logic.MealsDataSource
import logic.util.getRandomMeals
import logic.util.ofValidNameOrException
import model.Meal

class ExploreCountryFoodCultureUseCase(
    private val dataSource: MealsDataSource
) {
    fun getMealsOfCountry(country: String): List<Meal> {
        val validCountryName = country.ofValidNameOrException()
        return dataSource.getAllMeals()
            .filter { isMealRelatedToCountry(it, validCountryName) }
            .getRandomMeals(20)
    }

    private fun isMealRelatedToCountry(meal: Meal, country: String): Boolean {
        return with(meal) {
            isCountryExistInName(country) || isCountryExistInDescription(country) || isCountryExistInTags(country)
        }
    }

    private fun Meal.isCountryExistInName(country: String): Boolean {
        return name.contains(country, ignoreCase = true)
    }

    private fun Meal.isCountryExistInDescription(country: String): Boolean {
        return description.contains(country, ignoreCase = true)
    }

    private fun Meal.isCountryExistInTags(country: String): Boolean {
        return tags.any { it.contains(country, ignoreCase = true) }
    }
}