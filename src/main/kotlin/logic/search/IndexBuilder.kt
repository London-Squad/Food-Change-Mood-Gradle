package logic.search

interface IndexBuilder<K,T> {

    fun getIndex(): Map<K, T>
}