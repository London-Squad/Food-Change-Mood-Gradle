package logic

import model.Meal

interface IndexBuilder<K,T> {

    fun getIndex(): Map<K, T>
}