import data.FakeMealsDataSource
import data.csvData.CsvMealsDataSource
import data.csvData.CsvMealsParser
import data.FileReader
import data.csvData.CsvMealsDataSourceOneTimeLoad
import logic.SweetSuggester
import presentation.FoodChangeModeConsoleUI
import java.io.File
import java.util.Date
import model.Meal

fun main() {

    val csvFile = File("food.csv")
    val fileReader = FileReader(csvFile)
    val csvMealsParser = CsvMealsParser()

//    val mealsDataSource = FakeMealsDataSource()
//    val mealsDataSource = CsvMealsDataSource(fileReader, csvMealsParser)
    val mealsDataSource = CsvMealsDataSourceOneTimeLoad(fileReader, csvMealsParser, numberOfMealsToBeLoaded = -1)

    val ui = FoodChangeModeConsoleUI(sweetSuggester = SweetSuggester(mealsDataSource))
    ui.start()
}