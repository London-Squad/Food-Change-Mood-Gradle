package presentation.mealGuessGame

import logic.mealGuessGame.MealGuessGameUseCase
import presentation.BaseView

class MealGuessGameView(
    private val mealGuessGameUseCase: MealGuessGameUseCase
) : BaseView {

    override fun start() {
        printHeader()
        printRules()

        if (!mealGuessGameUseCase.isTwoOrMoreMealsAvailable()) {println("not enough meals available :'("); return}

        do {

            startGuessGame()
            print("\nEnter anything to continue or 0 to to exit: ")

        } while (readlnOrNull() != "0")
    }

    private fun startGuessGame() {

        val randomMeal = mealGuessGameUseCase.getRandomMeal()

        println("\nMeal Name: ${randomMeal.name}")
        var attempt = 1
        var result: String
        do {
            val guess = getValidGuess(attempt)
            result = mealGuessGameUseCase.checkGuessAttempt(guess, randomMeal.minutes!!)
            println(result)
            attempt++
        } while (!mealGuessGameUseCase.isAttemptExceeded(attempt) && result != "correct")

        if (result != "correct") {
            println("Game Over! the correct answer is ${randomMeal.minutes}")

        }
    }

    private fun printHeader() {
        println("------------------------------------------")
        println("             Meal Guess Game              ")
        println("------------------------------------------")
    }

    private fun printRules() {
        println("Rules: ")
        println("1. A random meal will be presented and you have to guess how many minutes are needed to prepare it.")
        println("2. You have 3 attempts only.")
        println("3. If the guess is incorrect, there will be hint for next attempt.\n")
    }

    private fun getValidGuess(attempt: Int): Int {

        print("Guess no.$attempt: ")
        val guessInput = readlnOrNull()?.toIntOrNull()
        if (guessInput == null) {
            println("invalid input")
            return getValidGuess(attempt)
        } else {
            return guessInput
        }
    }
}