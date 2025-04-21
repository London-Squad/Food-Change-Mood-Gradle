package presentation.mealGuessGame

import logic.mealGuessGame.MealGuessGameUseCase
import presentation.BaseView
import presentation.utils.CLIPrinter
import presentation.utils.UserInputReader

class MealGuessGameView(
    private val mealGuessGameUseCase: MealGuessGameUseCase,
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter
) : BaseView {
    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)

    override fun start() {
        printHeader()
        printRules()

        if (!mealGuessGameUseCase.isTwoOrMoreMealsAvailable()) {printLn("not enough meals available :'("); return}

        do {

            startGuessGame()
            cliPrinter.cliPrint("\nEnter anything to continue or 0 to to exit: ")

        } while (userInputReader.getUserInput() != "0")
    }

    private fun startGuessGame() {

        val randomMeal = mealGuessGameUseCase.getRandomMeal()

        printLn("\nMeal Name: ${randomMeal.name}")
        var attempt = 1
        var result: String
        do {
            val guess = getValidGuess(attempt)
            result = mealGuessGameUseCase.checkGuessAttempt(guess, randomMeal.minutes!!)
            printLn(result)
            attempt++
        } while (!mealGuessGameUseCase.isAttemptExceeded(attempt) && result != "correct")

        if (result != "correct") {
            printLn("Game Over! the correct answer is ${randomMeal.minutes}")

        }
    }

    private fun printHeader() {
        printLn("------------------------------------------")
        printLn("             Meal Guess Game              ")
        printLn("------------------------------------------")
    }

    private fun printRules() {
        printLn("Rules: ")
        printLn("1. A random meal will be presented and you have to guess how many minutes are needed to prepare it.")
        printLn("2. You have 3 attempts only.")
        printLn("3. If the guess is incorrect, there will be hint for next attempt.\n")
    }

    private fun getValidGuess(attempt: Int): Int {

        cliPrinter.cliPrint("Guess no.$attempt: ")
        val guessInput = userInputReader.getUserInput().toIntOrNull()
        if (guessInput == null) {
            printLn("invalid input")
            return getValidGuess(attempt)
        } else {
            return guessInput
        }
    }
}