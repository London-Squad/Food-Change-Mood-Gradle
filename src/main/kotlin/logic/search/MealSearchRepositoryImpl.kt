package logic.search

import logic.IndexBuilder
import logic.MealSearchRepository
import logic.SearchCache
import logic.TextSearchAlgorithm
import model.Meal

class MealSearchRepositoryImpl(
    private val meals: List<Meal>,
    private val searchAlgorithm: TextSearchAlgorithm,
    private val cache: SearchCache,
    private val indexBuilder: IndexBuilder
) : MealSearchRepository {
    private val invertedIndex: Map<String, Set<Int>> by lazy { indexBuilder.build(meals) }

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