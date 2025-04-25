package logic.search

class LevenshteinSearch(private val maxDistance: Int = 2) : TextSearchAlgorithm {
    override fun search(keyword: String, actualValue: String): Boolean =
        levenshteinDistance(keyword.lowercase(), actualValue.lowercase()) <= maxDistance


    private fun levenshteinDistance(source: String, target: String): Int {
        val matrix = createMatrix(source.length + 1, target.length + 1)

        for (row in 1..source.length) {
            for (col in 1..target.length) {
                val cost = if (source[row - 1] == target[col - 1]) 0 else 1
                matrix[row][col] = minOf(
                    matrix[row - 1][col] + 1,      // Deletion
                    matrix[row][col - 1] + 1,      // Insertion
                    matrix[row - 1][col - 1] + cost // Substitution
                )
            }
        }
        return matrix[source.length][target.length]
    }


    private fun createMatrix(rows: Int, cols: Int): Array<IntArray> =
        Array(rows) { row ->
            IntArray(cols) { col ->
                when {
                    row == 0 -> col // First row: cost of inserting target chars
                    col == 0 -> row // First column: cost of deleting source chars
                    else -> 0       // Inner cells: to be computed
                }
            }
        }
}