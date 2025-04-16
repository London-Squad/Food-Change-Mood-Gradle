import data.FileReader
import data.csvData.CsvMealsDataSource
import data.csvData.CsvMealsParser
import logic.GetIraqiMealsUseCase
import presentation.FoodChangeModeConsoleUI
import java.io.File

fun main() {

//    val mealsDataSource = FakeMealsDataSource()
    val csvFile = File("food.csv")
    val fileReader = FileReader(csvFile)
    val csvMealsParser = CsvMealsParser()
    val mealsDataSource = CsvMealsDataSource(fileReader, csvMealsParser)

    mealsDataSource.getAllMeals()
    //TODO: after create new feature, edit the UI class to present it then pass its useCase to the UI
//    val mealsDataSource = FakeMealsDataSource()
    val iraqiMealsUseCase = GetIraqiMealsUseCase(mealsDataSource)

    val ui = FoodChangeModeConsoleUI(iraqiMealsUseCase)
    ui.start()
}