package logic

import model.Meal

interface IndexBuilder<K,T> {
    fun build(meals: List<Meal>): Map<K, T>
}