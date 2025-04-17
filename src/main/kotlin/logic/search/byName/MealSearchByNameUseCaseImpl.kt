package logic.search.byName

import logic.*
import model.Meal

class MealSearchByNameUseCaseImpl(
    private val mealsDataSource: MealsDataSource,
    private val searchAlgorithm: TextSearchAlgorithm,
    private val cache: SearchCache,
    private val indexBuilder: IndexBuilder<String, Set<Int>>
) : MealSearchUseCase<List<Meal>> {

    override fun searchMeals(keyword: String): List<Meal> =
        cache.get(keyword) ?: run {
            val candidateIndices = keyword.lowercase()
                .split(" ")
                .flatMap { indexBuilder.index[it].orEmpty() } // Use indexBuilder.index
                .toSet()

            val results = if (candidateIndices.isEmpty()) {
                mealsDataSource.getAllMeals().filter { meal ->
                    val keywordWords = keyword.lowercase().split(" ")
                    keywordWords.all { kw ->
                        meal.name?.lowercase()?.split(" ")?.any { mw ->
                            searchAlgorithm.search(kw, mw)
                        } ?: false
                    }
                }
            } else {
                candidateIndices.mapNotNull { idx ->
                    mealsDataSource.getAllMeals().getOrNull(idx)?.takeIf { meal ->
                        val keywordWords = keyword.lowercase().split(" ")
                        keywordWords.all { kw ->
                            meal.name?.lowercase()?.split(" ")?.any { mw ->
                                searchAlgorithm.search(kw, mw)
                            } ?: false
                        }
                    }
                }
            }
            cache.put(keyword, results)
            results
        }
}