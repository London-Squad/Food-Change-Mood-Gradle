//import data.FakeMealsDataSource
import data.search.*
import logic.IndexBuilder
import data.FakeMealsDataSource
import data.csvData.CsvMealsDataSource
import data.csvData.CsvMealsParser
import data.FileReader
import data.csvData.CsvMealsDataSourceOneTimeLoad
import presentation.FoodChangeModeConsoleUI
import model.Meal
import model.Nutrition
import java.io.File
import java.time.LocalDate
import java.util.*
import java.io.File
import java.util.Date

fun main() {

    val csvFile = File("food.csv")
    val fileReader = FileReader(csvFile)
    val csvMealsParser = CsvMealsParser()

//    val mealsDataSource = FakeMealsDataSource()
//    val mealsDataSource = CsvMealsDataSource(fileReader, csvMealsParser)
    val mealsDataSource = CsvMealsDataSourceOneTimeLoad(fileReader, csvMealsParser, numberOfMealsToBeLoaded = 50)

//    val ui = FoodChangeModeConsoleUI()
//    ui.start()

    val meals = listOf(
        Meal(
            1u,
            "Spaghetti Bolognese",
            30u,
            101u,
            LocalDate.parse("2005-09-16"),
            listOf("pasta"),
            Nutrition(1f, 2f, 3f, 4f, 5f, 6f, 7f),
            5u,
            listOf("Boil pasta"),
            "Tasty",
            listOf("pasta", "meat"),
            2u
        ),
        Meal(
            2u,
            "Chicken Curry",
            45u,
            102u,
            LocalDate.parse("2005-09-16"),
            listOf("curry"),
            Nutrition(1f, 2f, 3f, 4f, 5f, 6f, 7f),
            6u,
            listOf("Cook chicken"),
            "Spicy",
            listOf("chicken", "spices"),
            3u
        ),
        Meal(
            2u,
            "arriba   baked winter squash mexican style",
            45u,
            102u,
            LocalDate.parse("2005-09-16"),
            listOf("curry"),
            Nutrition(1f, 2f, 3f, 4f, 5f, 6f, 7f),
            6u,
            listOf("Cook chicken"),
            "Spicy",
            listOf("chicken", "spices"),
            3u
        ),
        Meal(
            2u,
            "arriba   baked winter sqaaah mexican style",
            45u,
            102u,
            LocalDate.parse("2005-09-16"),
            listOf("curry"),
            Nutrition(1f, 2f, 3f, 4f, 5f, 6f, 7f),
            6u,
            listOf("Cook chicken"),
            "Spicy",
            listOf("chicken", "spices"),
            3u
        )
    )

    val searchAlgorithm = LevenshteinSearch(maxDistance = 3)
    val cache = InMemorySearchCache()
    val invertedIndexBuilder: IndexBuilder = InvertedIndexBuilder()
    val repository = MealSearchRepositoryImpl(meals, searchAlgorithm, cache, invertedIndexBuilder)
    val service = MealSearchService(repository)

    val results = service.search("squh")
    println(results.size)
}