package data.csvData

import model.Meal
import model.Nutrition
import java.time.LocalDate
import java.time.format.DateTimeParseException

class CsvMealsParser {
    fun parseTextToListOfMeals(text: String, numberOfMealsToBeLoaded: Int = -1): List<Meal> {
        val result: MutableList<Meal> = mutableListOf()
        val row: MutableList<String> = mutableListOf("")
        var openQuotation = false

        for (char in removeHeader(text)) {
            if (char == '\n' && !openQuotation) {
                try {
                    result.add(row.let(::parseRowStringToMeal))
                } catch (_: ListOfStringsCannotBeParsedToMeal) {
                    // skip
                }
                row.clear()
                row.add("")
            } else if (char == ',' && !openQuotation) {
                row.add("")
            } else if (char == '"') openQuotation = !openQuotation
            else row[row.lastIndex] = row.last() + char

            if (result.size == numberOfMealsToBeLoaded) break
        }
        return result
    }

    private fun removeHeader(text: String): String {
        val headerEndIndex = text.indexOfFirst { it == '\n' }
        return text.drop(headerEndIndex + 1)
    }

    private fun parseRowStringToMeal(row: List<String>): Meal =
        row.also {
            if (it.size != 12) throw ListOfStringsCannotBeParsedToMeal(
                "invalid number size of the list, expected 12, found $row.size"
            )
        }
            .map { str -> str.trim() }
            .let {
                Meal(
                    id = it[ColumnIndex.id].toIntOrNull() ?: throw ListOfStringsCannotBeParsedToMeal("invalid id"),
                    name = it[ColumnIndex.name].also { name -> if (name == "") throw ListOfStringsCannotBeParsedToMeal("invalid name") },
                    minutes = it[ColumnIndex.minutes].toIntOrNull()?.takeIf { minutes -> minutes > 0 },
                    dateSubmitted = it[ColumnIndex.dateSubmitted].let(::parseDate),
                    tags = it[ColumnIndex.tags].let(::parseStringOfListToListOfStrings),
                    nutrition = it[ColumnIndex.nutrition].let(::parseNutrition),
                    steps = it[ColumnIndex.steps].let(::parseStringOfListToListOfStrings),
                    description = it[ColumnIndex.description],
                    ingredients = it[ColumnIndex.ingredients].let(::parseStringOfListToListOfStrings),
                )
            }

    private fun parseDate(dateString: String): LocalDate? {
        return try {
            LocalDate.parse(dateString.trim())
        } catch (e: DateTimeParseException) {
            null
        }
    }

    private fun parseStringOfListToListOfStrings(stringOfList: String): List<String> {
        val result: MutableList<String> = mutableListOf("")
        var openQuot = false
        stringOfList.forEach { char ->
            if (char == ',' && !openQuot) result.add("")
            else if (char == '\'') openQuot = !openQuot
            else if (openQuot) result[result.lastIndex] = result[result.lastIndex] + char
        }
        return result
    }

    private fun parseNutrition(stringOfListOfInt: String): Nutrition {
        val nutritionValues = stringOfListOfInt
            .removeSurrounding("[", "]")
            .split(",")
            .map { singleNutritionString -> singleNutritionString.trim().toFloatOrNull()?.takeIf { it > 0 } }
        return Nutrition(
            nutritionValues[NutritionsIndex.calories],
            nutritionValues[NutritionsIndex.totalFat],
            nutritionValues[NutritionsIndex.sugar],
            nutritionValues[NutritionsIndex.sodium],
            nutritionValues[NutritionsIndex.protein],
            nutritionValues[NutritionsIndex.saturatedFat],
            nutritionValues[NutritionsIndex.carbohydrates],
        )
    }
}
