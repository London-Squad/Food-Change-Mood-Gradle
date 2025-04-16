import data.FakeMealsDataSource
import data.search.*
import logic.IndexBuilder
import data.csvData.CsvMealsParser
import data.FileReader
import data.csvData.CsvMealsDataSourceOneTimeLoad
import model.Meal
import model.Nutrition
import java.time.LocalDate
import java.io.File

fun main() {

    val csvFile = File("food.csv")
    val fileReader = FileReader(csvFile)
    val csvMealsParser = CsvMealsParser()

    val mealsDataSource = FakeMealsDataSource()
//    val mealsDataSource = CsvMealsDataSource(fileReader, csvMealsParser)
//    val mealsDataSource = CsvMealsDataSourceOneTimeLoad(fileReader, csvMealsParser, numberOfMealsToBeLoaded = 50)

//    val ui = FoodChangeModeConsoleUI()
//    ui.start()
    val searchAlgorithm = LevenshteinSearch(maxDistance = 3)
    val cache = InMemorySearchCache()
    val invertedIndexBuilder: IndexBuilder = InvertedIndexBuilder()
    val repository = MealSearchRepositoryImpl(mealsDataSource.getAllMeals(), searchAlgorithm, cache, invertedIndexBuilder)
    val service = MealSearchService(repository)

    val results = service.search("squh")
    println(results)
}