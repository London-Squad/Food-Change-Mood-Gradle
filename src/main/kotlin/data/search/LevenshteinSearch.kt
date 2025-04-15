package data.search

import logic.TextSearchAlgorithm

class LevenshteinSearch(private val maxDistance: Int = 2) : TextSearchAlgorithm {
    override fun search(keyword: String, actualValue: String): Boolean =
        levenshteinDistance(keyword.lowercase(), actualValue.lowercase()) <= maxDistance

    private fun levenshteinDistance(s1: String, s2: String): Int {
        // Initialize matrix declaratively
        val dp = Array(s1.length + 1) { i -> IntArray(s2.length + 1) { j -> if (i == 0) j else if (j == 0) i else 0 } }

        (1..s1.length).forEach { i ->
            (1..s2.length).forEach { j ->
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,      // Deletion
                    dp[i][j - 1] + 1,      // Insertion
                    dp[i - 1][j - 1] + cost // Substitution
                )
            }
        }
        return dp[s1.length][s2.length]
    }
}