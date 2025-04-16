package presentation

import logic.MealGuessGameUseCase

class MealGuessGameConsoleCLI(
    private val mealGuessGameUseCase: MealGuessGameUseCase
) {

    fun displayGame(){
        printHeader()
        printRules()

        mealGuessGameUseCase.startGuessGame()
    }

    private fun printHeader(){
        println("------------------------------------------")
        println("             Meal Guess Game              ")
        println("------------------------------------------")
    }
    private fun printRules(){
        println("Rules: ")
        println("1. A random meal will be presented and you have to guess the time needed to prepare it.")
        println("2. You have 3 attempts only.")
        println("3. If the guess is incorrect, there will be hint for next attempt.\n")
    }
}