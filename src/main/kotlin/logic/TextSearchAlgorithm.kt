package logic

interface TextSearchAlgorithm {
    fun search(keyword: String, actualValue: String): Boolean
}