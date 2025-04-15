import data.FakeMealsDataSource
import presentation.FoodChangeModeConsoleUI

fun main() {

    //TODO: after create new feature, edit the UI class to present it then pass its useCase to the UI
    val mealsDataSource = FakeMealsDataSource()

    val ui= FoodChangeModeConsoleUI()
    ui.start()

    val meals = listOf(
        Meal(1,"Spaghetti Bolognese",  30, 101, LocalDate.parse("2005-09-16"), listOf("pasta"),  Nutrition(1f,2f,3f,4f,5f,6f,7f), 5, listOf("Boil pasta"), "Tasty", listOf("pasta", "meat"), 2),
        Meal(2,"Spicy Chicken Curry",  45, 102, LocalDate.parse("2005-09-16"), listOf("curry"),  Nutrition(1f,2f,3f,4f,5f,6f,7f), 6, listOf("Cook chicken"), "Spicy", listOf("chicken", "spices"), 3)
    )

    val searchAlgorithm = LevenshteinSearch(maxDistance = 2)
    val cache = InMemorySearchCache()
    val repository = MealSearchRepositoryImpl(meals, searchAlgorithm, cache)
    val service = MealSearchService(repository)

    val results = service.search("Spicy Chckn Curry")
    println(results)
}