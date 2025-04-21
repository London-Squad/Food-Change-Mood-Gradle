package presentation.mealSearchByDate

import logic.search.MealSearchUseCase
import logic.search.byDate.MealSearchByDateUseCaseImpl
import model.Meal
import presentation.BaseView
import logic.util.InvalidDateFormatException
import logic.util.NoMealsFoundException
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader

class MealSearchByDateView(
    private val mealSearchUseCase: MealSearchUseCase<List<Pair<Int, String>>>,
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter,
    private val uiMealPrinter: UIMealPrinter
) : BaseView {

    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)

    override fun start() {
        printLn("Enter a date to search for meals (yyyy-MM-dd, e.g., 2023-04-16) or '0' to return to main menu:")
        val dateInput = userInputReader.getUserInput()
        if (dateInput == "0") return

        val searchResults = try {
            mealSearchUseCase.searchMeals(dateInput)
        } catch (e: InvalidDateFormatException) {
            printLn("Error: ${e.message}")
            return
        } catch (e: NoMealsFoundException) {
            printLn("Error: ${e.message}")
            return
        }

        if (searchResults.isEmpty()) {
            printLn("No meals found for date '$dateInput'.")
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

            userInput = userInputReader.getUserInput()

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
                            printLn("Error: ${e.message}")
                            continue
                        }
                        printMealAndWaitForEnter(meal)
                        break
                    } catch (e: NumberFormatException) {
                        printLn("Invalid input, please enter a valid meal ID or one of the options.")
                    }
                }
            }
        } while (mealsChunkIndex < chunkedMeals.size && mealsChunkIndex >= 0)
    }

    private fun printOptions(mealsChunkIndex: Int, mealsChunkSize: Int) {
        printLn()
        if ((mealsChunkIndex + 1) < mealsChunkSize) {
            printLn("If you want more meals, write 'next'")
        }
        if (mealsChunkIndex > 0) {
            printLn("If you want the previous meals, write 'back'")
        }
        printLn("If you want the details of a meal, enter its ID")
        printLn("If you want to go back to the main menu, enter 0")
        printLn()
        cliPrinter.cliPrint("Your input: ")
    }

    private fun printMeals(meals: List<Pair<Int, String>>, date: String) {
        printLn()
        printLn("---------------------------------------------")
        printLn("       Meals Added on '$date'                ")
        printLn("---------------------------------------------")

        meals.forEach { (id, name) ->
            printLn("ID: $id - $name")
        }
        printLn("---------------------------------------------")
    }

    private fun printMealAndWaitForEnter(meal: Meal) {
        uiMealPrinter.printMealDetails(meal)
        userInputReader.getUserInput("Press Enter to go back to main menu")
    }

    private companion object {
        const val MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE = 10
    }
}