import data.FakeMealsDataSource
import logic.IndexBuilder
import data.csvData.CsvMealsParser
import data.FileReader
import logic.search.*
import logic.search.byDate.MealDateInvertedIndexBuilder
import logic.search.byDate.MealSearchByDateRepositoryImpl
import logic.search.byName.InMemorySearchCache
import logic.search.byName.MealNameInvertedIndexBuilder
import logic.search.byName.MealSearchByNameRepositoryImpl
import utils.InvalidDateFormatException
import utils.NoMealsFoundException
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
//    val searchAlgorithm = LevenshteinSearch(maxDistance = 3)
//    val cache = InMemorySearchCache()
//    val invertedIndexBuilder: IndexBuilder<String,Set<Int>> = MealNameInvertedIndexBuilder()
//    val repository = MealSearchByNameRepositoryImpl(mealsDataSource.getAllMeals(), searchAlgorithm, cache, invertedIndexBuilder)
//    val results = repository.searchMeals("squh")
//    println(results)

    val repository = MealSearchByDateRepositoryImpl(
        meals = mealsDataSource.getAllMeals(),
        dateIndexBuilder = MealDateInvertedIndexBuilder()
    )

    try {
        val results = repository.searchMeals("2005-09-16")
        println("Meals on 2023-04-16: $results")
    } catch (e: InvalidDateFormatException) {
        println(e.message)
    } catch (e: NoMealsFoundException) {
        println(e.message)
    }

}