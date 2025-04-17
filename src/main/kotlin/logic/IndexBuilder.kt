package logic

import model.Meal

interface IndexBuilder<K,T> {
    val index: Map<K, T>

    fun build(meals: List<Meal>): Map<K, T>
}