package presentation.mealGuessGame

import logic.mealGuessGame.MealGuessGameUseCase
import model.Meal
import presentation.BaseView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader

class MealGuessGameView(
    private val mealGuessGameUseCase: MealGuessGameUseCase,
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter,
    private val uiMealPrinter: UIMealPrinter
) : BaseView {

    override fun start() {
        printHeader()
        printRules()

        if (!mealGuessGameUseCase.isGamePlayable()) {
            printLn("not enough meals available :'("); return
        }

        do {

            startNewRound()
            cliPrinter.cliPrint("\nEnter anything to continue or 0 to to exit: ")

        } while (userInputReader.getUserInput() != "0")
    }

    private fun startNewRound() {

        val meal = mealGuessGameUseCase.getRandomMeal()

        printLn("\nMeal Name: ${meal.name}")

        val result = startGuessGame(meal)

        if (result != MealGuessGameUseCase.GuessState.Correct) printLn("Game Over! the correct answer is ${meal.minutes}")
        else printLn("Well done!")
    }

    private fun startGuessGame(meal: Meal): MealGuessGameUseCase.GuessState {
        var attempt = 1
        var result: MealGuessGameUseCase.GuessState

        do {
            val guess = getValidGuessFromUser(attempt)
            result = mealGuessGameUseCase.checkGuess(guess, meal.minutes!!)
            printLn(result.state)
            attempt++
        } while (!mealGuessGameUseCase.isAttemptExceeded(attempt)
            && result != MealGuessGameUseCase.GuessState.Correct
        )
        return result
    }

    private fun printHeader() {
        uiMealPrinter.printHeader("Meal Guess Game")
    }

    private fun printRules() {
        printLn("Rules:")
        uiMealPrinter.printTextWithinWidth("1. A random meal will be presented and you have to guess how many minutes are needed to prepare it.")
        uiMealPrinter.printTextWithinWidth("2. You have 3 attempts only.")
        uiMealPrinter.printTextWithinWidth("3. If the guess is incorrect, there will be hint for next attempt.\n")
    }

    private fun getValidGuessFromUser(attempt: Int): Int =
        userInputReader.getValidUserInput(
            { it.toIntOrNull() != null },
            message = "Guess no.$attempt: ",
            invalidInputMessage = "Invalid number."
        ).toInt()

    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)
}
