//import data.FakeMealsDataSource
import presentation.FoodChangeModeConsoleUI
import data.search.InMemorySearchCache
import data.search.LevenshteinSearch
import data.search.MealSearchRepositoryImpl
import data.search.MealSearchService
import model.Meal
import model.Nutrition
import java.io.File
import java.time.LocalDate
import java.util.*

fun main() {

//    //TODO: after create new feature, edit the UI class to present it then pass its useCase to the UI
//    val mealsDataSource = FakeMealsDataSource()
//
//    val ui= FoodChangeModeConsoleUI()
//    ui.start()

    val meals = listOf(
        Meal(1u,"Spaghetti Bolognese",  30u, 101u, LocalDate.parse("2005-09-16"), listOf("pasta"),  Nutrition(1f,2f,3f,4f,5f,6f,7f), 5u, listOf("Boil pasta"), "Tasty", listOf("pasta", "meat"), 2u),
        Meal(2u,"Spicy Chicken Curry",  45u, 102u, LocalDate.parse("2005-09-16"), listOf("curry"),  Nutrition(1f,2f,3f,4f,5f,6f,7f), 6u, listOf("Cook chicken"), "Spicy", listOf("chicken", "spices"), 3u)
,   Meal(1u,"Spaghetti Bolognese",  30u, 101u, LocalDate.parse("2005-09-16"), listOf("pasta"),  Nutrition(1f,2f,3f,4f,5f,6f,7f), 5u, listOf("Boil pasta"), "Tasty", listOf("pasta", "meat"), 2u),
    Meal(1u,"Spaghetti Bolognese",  30u, 101u, LocalDate.parse("2005-09-16"), listOf("pasta"),  Nutrition(1f,2f,3f,4f,5f,6f,7f), 5u, listOf("Boil pasta"), "Tasty", listOf("pasta", "meat"), 2u),
    Meal(1u,"Spaghetti Bolognese",  30u, 101u, LocalDate.parse("2005-09-16"), listOf("pasta"),  Nutrition(1f,2f,3f,4f,5f,6f,7f), 5u, listOf("Boil pasta"), "Tasty", listOf("pasta", "meat"), 2u),
    Meal(1u,"Spaghetti Bolognese",  30u, 101u, LocalDate.parse("2005-09-16"), listOf("pasta"),  Nutrition(1f,2f,3f,4f,5f,6f,7f), 5u, listOf("Boil pasta"), "Tasty", listOf("pasta", "meat"), 2u),

    )

    val searchAlgorithm = LevenshteinSearch(maxDistance = 10)
    val cache = InMemorySearchCache()
    val repository = MealSearchRepositoryImpl(meals, searchAlgorithm, cache)
    val service = MealSearchService(repository)

    val results = service.search("Spcy chiken Curry")
    println(results)
}