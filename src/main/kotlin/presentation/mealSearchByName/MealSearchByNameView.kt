package presentation.mealSearchByName

import logic.search.MealSearchUseCase
import model.Meal
import presentation.BaseView
import utils.ViewUtil

class MealSearchByNameView(
    private val mealSearchUseCase: MealSearchUseCase<List<Meal>>,
    private val viewUtil: ViewUtil
) : BaseView {

    override fun start() {
        println("Enter a keyword to search for meals (or '0' to return to main menu):")
        val keyword = readlnOrNull() ?: ""
        if (keyword == "0") return

        val searchResults = mealSearchUseCase.searchMeals(keyword)
        if (searchResults.isEmpty()) {
            println("No meals found for keyword '$keyword'.")
            return
        }

        val chunkedMeals = searchResults.chunked(MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE)
        var mealsChunkIndex = 0
        var userInput: String?
        var mealsChunk: List<Meal>

        do {
            mealsChunk = chunkedMeals[mealsChunkIndex]

            printMealsNames(mealsChunk, keyword)
            printOptions(mealsChunkIndex, chunkedMeals.size)

            userInput = readln()

            when (userInput) {
                "next" -> mealsChunkIndex++
                "back" -> mealsChunkIndex--
                "0" -> break
                in (1..mealsChunk.size).map { it.toString() } -> {
                    printMealAndWaitForEnter(mealsChunk[userInput.toInt() - 1])
                    break
                }
                else -> println("Invalid input, try again")
            }
        } while (mealsChunkIndex < chunkedMeals.size && mealsChunkIndex >= 0)
    }

    private fun printOptions(mealsChunkIndex: Int, mealsChunkSize: Int) {
        println()
        if ((mealsChunkIndex + 1) < mealsChunkSize) {
            println("If you want more meals, write 'next'")
        }
        if (mealsChunkIndex > 0) {
            println("If you want the previous meals, write 'back'")
        }
        println("If you want the details of one of the meals, enter its number")
        println("If you want to go back to the main menu, enter 0")
        println()
        print("Your input: ")
    }

    private fun printMealsNames(meals: List<Meal>, keyword: String) {
        println()
        println("---------------------------------------------")
        println("          Search Results for '$keyword'       ")
        println("---------------------------------------------")

        meals.forEachIndexed { mealIndex, meal ->
            println("${mealIndex + 1}. ${meal.name}")
        }
        println("---------------------------------------------")
    }

    private fun printMealAndWaitForEnter(meal: Meal) {
        viewUtil.printMeal(meal)
        println("Press Enter to go back to main menu")
        readlnOrNull()
    }

    private companion object {
        const val MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE = 10
    }
}