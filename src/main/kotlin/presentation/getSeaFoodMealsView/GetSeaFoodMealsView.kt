package presentation.getSeaFoodMealsView

import logic.getSeaFoodMealsUseCase.GetSeaFoodMealsUseCase
import model.Meal
import presentation.BaseView
import presentation.utils.UserInputReader
import presentation.utils.ViewUtil

class GetSeaFoodMealsView(
    private val getSeaFoodMealsUseCase: GetSeaFoodMealsUseCase,
    private val viewUtil: ViewUtil,
    private val userInputReader: UserInputReader
): BaseView {
    override fun start() {
        val chunkedMeals = getSeaFoodMealsUseCase
            .getSeaFoodMealsSortedByProtein()
            .chunked(MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE)

        if (chunkedMeals.isEmpty()) {println("no meals found :'("); return}

        var userInput: String?
        var mealsChunkIndex = 0
        var mealsChunk: List<Meal>

        do {
            mealsChunk = chunkedMeals[mealsChunkIndex]

            printMealsNames(mealsChunk, mealsChunkIndex)
            printOptions(mealsChunkIndex, chunkedMeals.size)

            userInput = userInputReader.getUserInput()

            when (userInput) {
                "next" -> mealsChunkIndex++
                "back" -> mealsChunkIndex--
                "0" -> break
                in (1+mealsChunkIndex* MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE..mealsChunk.size+mealsChunkIndex* MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE).map { it.toString() } -> {
                    printMealAndWaitForEnter(
                        mealsChunk[userInput.toInt() - 1 - mealsChunkIndex* MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE]
                    )
                    break
                }

                else -> println("Invalid input, try again")
            }

        } while (mealsChunkIndex < chunkedMeals.size && mealsChunkIndex >= 0)
    }

    private fun printOptions(mealsChunkIndex: Int, mealsChunksize: Int) {
        println()
        if ((mealsChunkIndex + 1) < mealsChunksize) {
            println("If you want more meals, write 'next'")
        }
        if (mealsChunkIndex > 0) {
            println("If you want the previous meals, write 'back'")
        }
        println("If you want the details of one of the meal, enter its number")
        println("If you want to go back to the main menu, enter 0")
        println()
        print("your input: ")
    }

    private fun printMealsNames(meals: List<Meal>, baseIndex: Int = 0) {

        println()
        println("---------------------------------------------")
        println("               Sea Food Meals                ")
        println("---------------------------------------------")

        meals.forEachIndexed { mealIndex, meal ->
            println("${mealIndex + 1 + baseIndex* MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE}. ${meal.name} | protein = ${meal.nutrition.protein}")
        }
        println("---------------------------------------------")
    }

    private fun printMealAndWaitForEnter(meal: Meal) {
        viewUtil.printMeal(meal)
        println("press Enter to go back to main menu")
        readlnOrNull()
    }

    private companion object {
        const val MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE = 10
    }
}