package data.search

import logic.SearchCache
import model.Meal

class InMemorySearchCache : SearchCache {
    private var cache: Map<String, List<Meal>> = emptyMap()

    override fun get(keyword: String): List<Meal>? =
        cache[keyword.lowercase()]

    override fun put(keyword: String, meals: List<Meal>) {
        cache = cache + (keyword.lowercase() to meals)
    }
}