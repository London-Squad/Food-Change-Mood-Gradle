package logic.search

import logic.MealSearchRepository
import model.Meal

class MealSearchService(
    private val mealSearchRepositoryImpl: MealSearchRepository
) {
    fun search(keyword: String): List<Meal> =
        mealSearchRepositoryImpl.searchMeals(keyword)
}