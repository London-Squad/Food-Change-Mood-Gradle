import data.FakeMealsDataSource
import data.csvData.CsvMealsDataSource
import data.csvData.CsvMealsParser
import data.FileReader
import data.csvData.CsvMealsDataSourceOneTimeLoad
import logic.MealGuessGameUseCase
import presentation.FoodChangeModeConsoleUI
import presentation.MealGuessGameConsoleCLI
import java.io.File
import java.util.Date

fun main() {

    val csvFile = File("food.csv")
    val fileReader = FileReader(csvFile)
    val csvMealsParser = CsvMealsParser()

//    val mealsDataSource = FakeMealsDataSource()
//    val mealsDataSource = CsvMealsDataSource(fileReader, csvMealsParser)
    val mealsDataSource = CsvMealsDataSourceOneTimeLoad(fileReader, csvMealsParser, numberOfMealsToBeLoaded = 50)

    val mealGuessGame = MealGuessGameUseCase(mealsDataSource)

    val ui = FoodChangeModeConsoleUI(mealGuessGameConsoleCLI = MealGuessGameConsoleCLI(mealGuessGame))
    ui.start()
}