package logic.search.byDate

import logic.search.IndexBuilder
import logic.MealsDataSource
import model.Meal

class IdIndexBuilder(
    private val mealsDataSource: MealsDataSource
) : IndexBuilder<Int, Int> {
    private val index: Map<Int, Int>

    init {
        index = build(mealsDataSource.getAllMeals())
    }

    private fun build(meals: List<Meal>): Map<Int, Int> =
        meals.withIndex().associate { (idx, meal) -> meal.id to idx }

    override fun getIndex(): Map<Int, Int> {
        return index
    }
}