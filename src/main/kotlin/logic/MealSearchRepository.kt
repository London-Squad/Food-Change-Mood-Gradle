package logic

import model.Meal

interface MealSearchRepository {
    fun searchMeals(keyword: String): List<Meal>
}