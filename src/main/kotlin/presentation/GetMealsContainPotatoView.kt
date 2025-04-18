package presentation

import logic.GetMealsContainPotatoUseCase
import model.Meal

class GetMealsContainPotatoView(
    private val getMealsContainPotatoUseCase: GetMealsContainPotatoUseCase,
    private val viewUtil: ViewUtil
) : BaseView {

    override fun start() {
        printHeader()
        val randomPotatoMeals = getMealsContainPotatoUseCase.getRandomMeals()

        if (randomPotatoMeals.isEmpty()) {
            println("There is no meals made from potato.")
            return
        }

        println("List of meals that contain potatoes:\n")
        printMealsNames(randomPotatoMeals)
        println("If you want the details of one of the meal, enter its number")
        println("If you want to go back to the main menu, enter 0")
        println()
        print("your input: ")
        do {

            when (val userInput = readlnOrNull()) {

                "0" -> return
                in (1..randomPotatoMeals.size).map { it.toString() } -> {
                    printMealAndWaitForEnter(
                        randomPotatoMeals[userInput!!.toInt() - 1]
                    )
                    break
                }

                else -> println("Invalid input, try again")
            }
        } while (true)
    }

    private fun printHeader() {
        println("------------------------------------------")
        println("              I Love Potato               ")
        println("------------------------------------------")
    }

    private fun printMealsNames(meals: List<Meal>) {

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

}