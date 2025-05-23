package logic.search.byName

import logic.search.SearchCache
import model.Meal

class InMemorySearchCache : SearchCache {
    private val cache: MutableMap<String, List<Meal>> = mutableMapOf()

    override fun get(keyword: String): List<Meal>? =
        cache[keyword.lowercase()]

    override fun put(keyword: String, meals: List<Meal>) {
        cache[keyword.lowercase()] = meals
    }
}