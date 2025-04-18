package logic.search

interface TextSearchAlgorithm {
    fun search(keyword: String, actualValue: String): Boolean
}