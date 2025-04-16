import data.FileReader
import data.csvData.CsvMealsDataSource
import data.csvData.CsvMealsParser
import logic.GetItalianFoodForLargeGroupUseCase
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
    val mealsDataSource = CsvMealsDataSource(fileReader, csvMealsParser)
//    val mealsDataSource = CsvMealsDataSourceOneTimeLoad(fileReader, csvMealsParser, numberOfMealsToBeLoaded = 50)
//    val mealsDataSource = CsvMealsDataSourceOneTimeLoad(fileReader, csvMealsParser, numberOfMealsToBeLoaded = -1)

//    val ui = FoodChangeModeConsoleUI()
//    ui.start()
    val searchAlgorithm = LevenshteinSearch(maxDistance = 3)
    val cache = InMemorySearchCache()
    val invertedIndexBuilder: IndexBuilder = InvertedIndexBuilder()
    val repository = MealSearchRepositoryImpl(mealsDataSource, searchAlgorithm, cache, invertedIndexBuilder)

    val results = repository.searchMeals("squh")
//    println(results)

    val italianMealsUseCase = GetItalianFoodForLargeGroupUseCase(mealsDataSource)

    val ui = FoodChangeModeConsoleUI(italianMealsUseCase)
    ui.start()

}