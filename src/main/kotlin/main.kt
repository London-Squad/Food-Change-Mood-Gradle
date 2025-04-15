import data.csvData.CsvMealsDataSource
import data.csvData.CsvMealsParser
import data.FileReader
import presentation.FoodChangeModeConsoleUI
import java.io.File

fun main() {

//    val mealsDataSource = FakeMealsDataSource()
    val csvFile = File("food.csv")
    val fileReader = FileReader(csvFile)
    val csvMealsParser = CsvMealsParser()
    val mealsDataSource = CsvMealsDataSource(fileReader, csvMealsParser)

    mealsDataSource.getAllMeals()

    val ui = FoodChangeModeConsoleUI()
    ui.start()
}