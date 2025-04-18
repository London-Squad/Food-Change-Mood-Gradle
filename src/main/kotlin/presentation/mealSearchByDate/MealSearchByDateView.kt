package presentation.mealSearchByDate

import logic.search.MealSearchUseCase
import logic.search.byDate.MealSearchByDateUseCaseImpl
import model.Meal
import presentation.BaseView
import presentation.utils.ViewUtil
import logic.util.InvalidDateFormatException
import logic.util.NoMealsFoundException

class MealSearchByDateView(
    private val mealSearchUseCase: MealSearchUseCase<List<Pair<Int, String>>>,
    private val viewUtil: ViewUtil
) : BaseView {

    override fun start() {
        println("Enter a date to search for meals (yyyy-MM-dd, e.g., 2023-04-16) or '0' to return to main menu:")
        val dateInput = readlnOrNull() ?: ""
        if (dateInput == "0") return

        val searchResults = try {
            mealSearchUseCase.searchMeals(dateInput)
        } catch (e: InvalidDateFormatException) {
            println("Error: ${e.message}")
            return
        } catch (e: NoMealsFoundException) {
            println("Error: ${e.message}")
            return
        }

        if (searchResults.isEmpty()) {
            println("No meals found for date '$dateInput'.")
            return
        }

        val chunkedMeals = searchResults.chunked(MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE)
        var mealsChunkIndex = 0
        var userInput: String?
        var mealsChunk: List<Pair<Int, String>>

        do {
            mealsChunk = chunkedMeals[mealsChunkIndex]

            printMeals(mealsChunk, dateInput)
            printOptions(mealsChunkIndex, chunkedMeals.size)

            userInput = readln()

            when (userInput) {
                "next" -> mealsChunkIndex++
                "back" -> mealsChunkIndex--
                "0" -> break
                else -> {
                    try {
                        val mealId = userInput.toInt()
                        val meal = try {
                            (mealSearchUseCase as MealSearchByDateUseCaseImpl).getMealDetails(mealId)
                        } catch (e: NoMealsFoundException) {
                            println("Error: ${e.message}")
                            continue
                        }
                        printMealAndWaitForEnter(meal)
                        break
                    } catch (e: NumberFormatException) {
                        println("Invalid input, please enter a valid meal ID or one of the options.")
                    }
                }
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
        println("If you want the details of a meal, enter its ID")
        println("If you want to go back to the main menu, enter 0")
        println()
        print("Your input: ")
    }

    private fun printMeals(meals: List<Pair<Int, String>>, date: String) {
        println()
        println("---------------------------------------------")
        println("       Meals Added on '$date'                ")
        println("---------------------------------------------")

        meals.forEach { (id, name) ->
            println("ID: $id - $name")
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