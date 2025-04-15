package data

import logic.MealsDataSource
import model.Meal

class CsvMealsDataSource(
    private val fileLoader: FileLoader,
    private val parser : CsvMealsParser
) : MealsDataSource {
    override fun getAllMeals() : List<Meal> {
        return fileLoader.getLines().slice(1..11).map{ line -> parser.parseLine(line) }
    }
}