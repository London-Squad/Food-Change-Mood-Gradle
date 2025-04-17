package logic.search.byName

import logic.IndexBuilder
import logic.MealsDataSource
import model.Meal

class MealNameInvertedIndexBuilder(
    private val mealsDataSource: MealsDataSource
) : IndexBuilder<String, Set<Int>> {
    override val index: Map<String, Set<Int>>

    init {
        index = build(mealsDataSource.getAllMeals())
    }

    override fun build(meals: List<Meal>): Map<String, Set<Int>> =
        meals.withIndex()
            .flatMap { (idx, meal) ->
                meal.name.lowercase()
                    .split(" ")
                    .map { word -> word to idx }
            }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.toSet() }
}