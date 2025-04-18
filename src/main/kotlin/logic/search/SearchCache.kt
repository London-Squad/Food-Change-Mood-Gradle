package logic.search

import model.Meal

interface SearchCache {
    fun get(keyword: String): List<Meal>?
    fun put(keyword: String, meals: List<Meal>)
}