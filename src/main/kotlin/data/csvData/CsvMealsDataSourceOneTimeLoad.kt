package data.csvData

import data.FileReader
import logic.MealsDataSource
import model.Meal

class CsvMealsDataSourceOneTimeLoad (
    private val fileReader: FileReader,
    private val parser : CsvMealsParser,
    private val numberOfMealsToBeLoaded: Int = -1
) : MealsDataSource {

    private val AllMeals = parser.parseTextToListOfMeals(fileReader.getText(), numberOfMealsToBeLoaded)

    override fun getAllMeals() : List<Meal> {
        return AllMeals
    }
}
