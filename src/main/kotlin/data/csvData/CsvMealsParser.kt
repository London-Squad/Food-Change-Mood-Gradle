package data.csvData

import model.Meal
import model.Nutrition
import java.time.LocalDate

class CsvMealsParser {
    fun parseTextToListOfMeals(text: String): List<Meal> {
        val result: MutableList<Meal> = mutableListOf()
        val row: MutableList<String> = mutableListOf("")
        var openQuotation = false

        for (char in removeHeader(text)) {
            if (char == '\n' && !openQuotation) {
                result.add(parseRowStringToMeal(row))
                row.clear()
                row.add("")
            } else if (char == ',' && !openQuotation) row.add("")
            else if (char == '"') openQuotation = !openQuotation
            else row[row.lastIndex] = row.last() + char
        }
        return result
    }

    private fun removeHeader(text: String): String {
        val headerEndIndex = text.indexOfFirst { it == '\n' }
        return text.drop(headerEndIndex + 1)
    }

    private fun parseRowStringToMeal(row: List<String>): Meal {
        return Meal(
            id = row[ColumnIndex.id].trim().toUInt(),
            name = row[ColumnIndex.name],
            minutes = row[ColumnIndex.minutes].trim().toUInt(),
            contributorId = row[ColumnIndex.contributorId].trim().toUInt(),
            submitted = LocalDate.parse(row[ColumnIndex.submitted].trim()),
            tags = stringOfListToListOfStrings(row[ColumnIndex.tags].trim()),
            nutrition = parseNutrition(row[ColumnIndex.nutrition].trim()),
            numberOfSteps = row[ColumnIndex.numberOfSteps].trim().toUInt(),
            steps = stringOfListToListOfStrings(row[ColumnIndex.steps].trim()),
            description = row[ColumnIndex.description],
            ingredients = stringOfListToListOfStrings(row[ColumnIndex.ingredients]),
            numberOfIngredients = row[ColumnIndex.numberOfIngredients].trim().toUInt()
        )
    }

    private fun stringOfListToListOfStrings(stringOfList: String): List<String?> {
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
        val list = stringOfListOfInt.slice(1..stringOfListOfInt.length-2)
            .split(",")
            .map{it.trim().toFloatOrNull()}
        return Nutrition(
            list[NutritionsIndex.calories],
            list[NutritionsIndex.totalFat],
            list[NutritionsIndex.sugar],
            list[NutritionsIndex.sodium],
            list[NutritionsIndex.protein],
            list[NutritionsIndex.saturatedFat],
            list[NutritionsIndex.carbohydrates],
            )
    }
}
