package logic.search

import logic.IndexBuilder
import model.Meal

class InvertedIndexBuilder : IndexBuilder {
    override fun build(meals: List<Meal>): Map<String, Set<Int>> =
        meals.withIndex()
            .flatMap { (idx, meal) ->
                meal.name!!.lowercase()
                    .split(" ")
                    .map { word -> word to idx }
            }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.toSet() }
}