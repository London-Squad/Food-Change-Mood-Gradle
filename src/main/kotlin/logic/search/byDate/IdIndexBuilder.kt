package logic.search.byDate

import logic.IndexBuilder
import logic.MealsDataSource
import model.Meal

class IdIndexBuilder(
    private val mealsDataSource: MealsDataSource
) : IndexBuilder<Int, Int> {
    override val index: Map<Int, Int>

    init {
        index = build(mealsDataSource.getAllMeals())
    }

    override fun build(meals: List<Meal>): Map<Int, Int> =
        meals.withIndex().associate { (idx, meal) -> meal.id to idx }
}