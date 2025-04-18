package presentation.italianFoodForLargeGroup

import logic.getItalianFoodForLargeGroup.GetItalianFoodForLargeGroupUseCase
import model.Meal
import presentation.BaseView
import presentation.utils.ViewUtil

class ItalianFoodForLargeGroupView(
    private val getItalianFoodForLargeGroupUseCase: GetItalianFoodForLargeGroupUseCase,
    private val viewUtil: ViewUtil

) : BaseView {
    override fun start() {
        val chunkedItalianMeals = getItalianFoodForLargeGroupUseCase.getItalianMealsForLargeGroup()
            .chunked(MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE)

        var userInput: String?
        var mealsChunkIndex = 0
        var mealsChunk: List<Meal>

        do {
            mealsChunk = chunkedItalianMeals[mealsChunkIndex]

            printMealsNames(mealsChunk)
            printOptions(mealsChunkIndex, chunkedItalianMeals.size)

            userInput = readln()

            when (userInput) {
                "next" -> mealsChunkIndex++
                "back" -> mealsChunkIndex--
                "0" -> break
                in (1..mealsChunk.size).map { it.toString() } -> {
                    printMealAndWaitForEnter(
                        mealsChunk[userInput.toInt() - 1]
                    )
                    break
                }

                else -> println("Invalid input, try again")
            }

        } while (mealsChunkIndex < chunkedItalianMeals.size && mealsChunkIndex >= 0)
    }


    private fun printOptions(mealsChunkIndex: Int, mealsChunkSize: Int) {
        println()
        if ((mealsChunkIndex + 1) < mealsChunkSize) {
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

    private fun printMealsNames(meals: List<Meal>) {

        println()
        println("---------------------------------------------")
        println("        Italian Meals Fore Large Group       ")
        println("---------------------------------------------")

        meals.forEachIndexed { mealIndex, meal ->
            println("${mealIndex + 1}. ${meal.name}")
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