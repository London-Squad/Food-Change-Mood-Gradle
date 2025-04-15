package data.csvData

import data.FileReader
import logic.MealsDataSource
import model.Meal

class CsvMealsDataSource(
    private val fileReader: FileReader,
    private val parser : CsvMealsParser
) : MealsDataSource {
    override fun getAllMeals() : List<Meal> {
        return parser.parseTextToListOfMeals(fileReader.getText())
    }
}