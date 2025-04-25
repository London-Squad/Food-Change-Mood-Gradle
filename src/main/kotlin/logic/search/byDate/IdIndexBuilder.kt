package logic.search.byDate

import logic.MealsDataSource
import model.Meal

class IdIndexBuilder(
    mealsDataSource: MealsDataSource
) {
    private val index: Map<Int, Int>

    init {
        index = build(mealsDataSource.getAllMeals())
    }

    private fun build(meals: List<Meal>): Map<Int, Int> =
        meals.withIndex().associate { (idx, meal) -> meal.id to idx }

    fun getIndex(): Map<Int, Int> {
        return index
    }
}