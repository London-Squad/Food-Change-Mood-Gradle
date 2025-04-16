package data.search

import logic.MealSearchRepository
import logic.SearchCache
import logic.TextSearchAlgorithm
import model.Meal

class MealSearchRepositoryImpl(
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
        cache.get(keyword) ?: run {
            val candidateIndices = keyword.lowercase()
                .split(" ")
                .flatMap { invertedIndex[it].orEmpty() }
                .toSet()

            val results = if (candidateIndices.isEmpty()) {
                meals.filter { meal ->
                    val keywordWords = keyword.lowercase().split(" ")
                    keywordWords.any { kw ->
                        meal.name!!.lowercase().split(" ").any { mw ->
                            searchAlgorithm.search(kw, mw)
                        }
                    }
                }
            } else {
                candidateIndices.mapNotNull { idx ->
                    meals.getOrNull(idx)?.takeIf { meal ->
                        val keywordWords = keyword.lowercase().split(" ")
                        keywordWords.any { kw ->
                            meal.name!!.lowercase().split(" ").any { mw ->
                                searchAlgorithm.search(kw, mw)
                            }
                        }
                    }
                }
            }
            cache.put(keyword, results)
            results
        }
}