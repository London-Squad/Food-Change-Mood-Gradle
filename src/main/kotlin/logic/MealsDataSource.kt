package logic

import model.Meal

interface MealsDataSource {
    fun getAllMeals() : List<Meal>
}