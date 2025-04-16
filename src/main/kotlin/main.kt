import data.FileReader
import data.csvData.CsvMealsDataSourceOneTimeLoad
import data.csvData.CsvMealsParser
import logic.GetIraqiMealsUseCase
import logic.IndexBuilder
import logic.search.InMemorySearchCache
import logic.search.InvertedIndexBuilder
import logic.search.LevenshteinSearch
import logic.search.MealSearchRepositoryImpl
import presentation.FoodChangeModeConsoleUI
import java.io.File

fun main() {

    val csvFile = File("food.csv")
    val fileReader = FileReader(csvFile)
    val csvMealsParser = CsvMealsParser()

//    val mealsDataSource = FakeMealsDataSource()
//    val mealsDataSource = CsvMealsDataSource(fileReader, csvMealsParser)
//    val mealsDataSource = CsvMealsDataSourceOneTimeLoad(fileReader, csvMealsParser, numberOfMealsToBeLoaded = 50)
    val mealsDataSource = CsvMealsDataSourceOneTimeLoad(fileReader, csvMealsParser, numberOfMealsToBeLoaded = -1)
    mealsDataSource.getAllMeals()
    //TODO: after create new feature, edit the UI class to present it then pass its useCase to the UI
//    val mealsDataSource = FakeMealsDataSource()
    val iraqiMealsUseCase = GetIraqiMealsUseCase(mealsDataSource)

//    val ui = FoodChangeModeConsoleUI()
//    ui.start()
    val searchAlgorithm = LevenshteinSearch(maxDistance = 3)
    val cache = InMemorySearchCache()
    val invertedIndexBuilder: IndexBuilder = InvertedIndexBuilder()
    val repository = MealSearchRepositoryImpl(mealsDataSource, searchAlgorithm, cache, invertedIndexBuilder)

    val results = repository.searchMeals("squh")
//    println(results)
    val ui = FoodChangeModeConsoleUI(iraqiMealsUseCase)
    ui.start()
}