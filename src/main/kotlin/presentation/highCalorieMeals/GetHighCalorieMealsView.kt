package presentation.highCalorieMeals

import logic.getHighCalorieMeals.GetHighCalorieMealsUseCase
import model.Meal
import presentation.BaseView
import presentation.utils.ViewUtil

class GetHighCalorieMealsView(
    private val getHighCalorieMealsUseCase: GetHighCalorieMealsUseCase,
    private val viewUtil: ViewUtil
) : BaseView {

    override fun start() {
        val chunkedHighCalorieMeals = getHighCalorieMealsUseCase.getHighCalorieMeals()
            .chunked(MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE)

        var userInput: String?
        var mealsChunkIndex = 0
        var mealsChunk: List<Meal>

        do {
            if (chunkedHighCalorieMeals.isEmpty()) {
                println("No meals with more than 700 calories found.")
                break
            }

            mealsChunk = chunkedHighCalorieMeals[mealsChunkIndex]

            printMealsNames(mealsChunk)
            printOptions(mealsChunkIndex, chunkedHighCalorieMeals.size)

            userInput = readln()

            when (userInput) {
                "next" -> if (mealsChunkIndex < chunkedHighCalorieMeals.size - 1) mealsChunkIndex++
                "back" -> if (mealsChunkIndex > 0) mealsChunkIndex--
                "0" -> break
                in (1..mealsChunk.size).map { it.toString() } -> {
                    printMealAndWaitForEnter(mealsChunk[userInput.toInt() - 1])
                    break
                }

                else -> println("Invalid input, try again")
            }

        } while (mealsChunkIndex < chunkedHighCalorieMeals.size && mealsChunkIndex >= 0)
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
        print("your input: ")
    }

    private fun printMealsNames(meals: List<Meal>) {
        println()
        println("---------------------------------------------")
        println("        High Calorie Meals (>700 kcal)       ")
        println("---------------------------------------------")

        meals.forEachIndexed { mealIndex, meal ->
            println("${mealIndex + 1}. ${meal.name} (${meal.nutrition.calories} kcal)")
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