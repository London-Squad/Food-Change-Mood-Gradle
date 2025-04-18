package presentation.gymHelper

import logic.gymHelper.GymHelperUseCase
import model.Meal
import presentation.BaseView
import presentation.utils.ViewUtil

class GymHelperView(
    private val gymHelperUseCase: GymHelperUseCase,
    private val viewUtil: ViewUtil
) : BaseView {

    override fun start() {
        val calories = getValidFloatInput("Enter desired calories:")
        val protein = getValidFloatInput("Enter desired protein:")
        println("Enter approximate percentage (e.g., 10 for ±10%, default 10):")
        val approximate = readlnOrNull()
            ?.toDoubleOrNull()
            ?.let { if (it < 0) -it else it }
            ?.div(100)
            ?: 0.1

        val matchingMeals = gymHelperUseCase.getGymMembersMeals(calories, protein, approximate)
        if (matchingMeals.isEmpty()) {
            println("Sorry, no meals match your criteria.")
            println("Press Enter to return to main menu.")
            readlnOrNull()
            return
        }

        val chunkedMeals = matchingMeals.chunked(MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE)
        var chunkIndex = 0
        var userInput: String?

        do {
            val mealsChunk = chunkedMeals[chunkIndex]
            printMealsNames(mealsChunk)
            printOptions(chunkIndex, chunkedMeals.size)

            userInput = readln()

            when (userInput) {
                "next" -> chunkIndex++
                "back" -> chunkIndex--
                "0" -> break
                in (1..mealsChunk.size).map { it.toString() } -> {
                    printMealAndWaitForEnter(mealsChunk[userInput.toInt() - 1])
                    break
                }

                else -> println("Invalid input, try again")
            }
        } while (chunkIndex in chunkedMeals.indices)
    }

    private fun printOptions(chunkIndex: Int, chunkSize: Int) {
        println()
        if (chunkIndex + 1 < chunkSize) {
            println("If you want more meals, write 'next'")
        }
        if (chunkIndex > 0) {
            println("If you want the previous meals, write 'back'")
        }
        println("If you want the details of one of the meals, enter its number")
        println("If you want to go back to the main menu, enter 0")
        println()
        print("Your input: ")
    }

    private fun printMealsNames(meals: List<Meal>) {
        println()
        println("---------------------------------------------")
        println("            Gym-Friendly Meals               ")
        println("---------------------------------------------")
        meals.forEachIndexed { index, meal ->
            println("${index + 1}. ${meal.name}")
        }
        println("---------------------------------------------")
    }

    private fun printMealAndWaitForEnter(meal: Meal) {
        viewUtil.printMeal(meal)
        println("Press Enter to go back to main menu")
        readlnOrNull()
    }

    private fun getValidFloatInput(prompt: String): Float {
        while (true) {
            print("$prompt ")
            val input = readlnOrNull()
            val number = input?.toFloatOrNull()
            if (number != null && number >= 0) return number
            println("❌ Invalid input. Please enter a valid number.")
        }
    }

    private companion object {
        const val MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE = 10
    }
}