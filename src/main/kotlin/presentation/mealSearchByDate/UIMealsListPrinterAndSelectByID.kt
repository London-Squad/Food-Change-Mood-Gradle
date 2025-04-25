package presentation.mealSearchByDate

import logic.search.MealSearchUseCase
import logic.search.byDate.MealSearchByDateUseCaseImpl
import logic.util.NoMealsFoundException
import model.Meal
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader

class UIMealsListPrinterAndSelectByID (
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter,
    private val uiMealPrinter: UIMealPrinter
) {

    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)

    fun printMeals(
        dateInput: String,
        meals: List<Pair<Int, String>>,
        mealSearchUseCase: MealSearchUseCase<List<Pair<Int, String>>>
    ) {
        val chunkedMeals = meals.chunked(MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE)
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
        uiMealPrinter.printHeader("Meals Added on '$date'")

        meals.forEach { (id, name) ->
            printLn("ID: $id - $name")
        }
        printLn(uiMealPrinter.getThinHorizontal())
    }

    private fun printMealAndWaitForEnter(meal: Meal) {
        uiMealPrinter.printMealDetails(meal)
        userInputReader.getUserInput("Press Enter to go back to main menu")
    }

    private companion object {
        const val MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE = 10
    }

}