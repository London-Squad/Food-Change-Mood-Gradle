package data.search

import logic.MealSearchRepository
import logic.SearchCache
import logic.TextSearchAlgorithm
import model.Meal

class MealSearchRepositoryImpl (
    private val meals: List<Meal>,
    private val searchAlgorithm: TextSearchAlgorithm,
    private val cache: SearchCache
) : MealSearchRepository {
    private val invertedIndex: Map<String, Set<Int>> by lazy { buildInvertedIndex() }

    private fun buildInvertedIndex(): Map<String, Set<Int>> =
        meals.withIndex()
            .flatMap { (idx, meal) ->
                meal.name!!.lowercase()
                    .split(" ")
                    .map { word -> word to idx }
            }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.toSet() }

    override fun searchMeals(keyword: String): List<Meal> =
        cache.get(keyword)?.let { it } ?: run {
            val candidateIndices = keyword.lowercase()
                .split(" ")
                .flatMap { invertedIndex[it].orEmpty() }
                .toSet()

            val results = meals.filterIndexed { idx, meal ->
                candidateIndices.contains(idx) && searchAlgorithm.search(keyword, meal.name!!)
            }

            cache.put(keyword, results)
            results
        }
}