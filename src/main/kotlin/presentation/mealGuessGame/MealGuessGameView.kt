package presentation.mealGuessGame

import logic.mealGuessGame.MealGuessGameUseCase
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

        mealGuessGameUseCase.initGame()
        val mealName = mealGuessGameUseCase.getRandomMealNameWithValidTime()
        printLn("\nMeal Name: $mealName")

        giveUserNewAttempt()
    }

    private fun giveUserNewAttempt() {
        val userGuess = getValidGuessFromUser()
        selectNextCLI(userGuess)
    }

    private fun selectNextCLI(userGuess: Int) {
        val result = mealGuessGameUseCase.evaluateGuessAttempt(userGuess)
        if (result == MealGuessGameUseCase.GuessState.Correct) {
            printWinMessage()
            return
        }

        printLn(result.state)
        if (mealGuessGameUseCase.isMaxAttemptExceeded()) {
            printLn("Game Over! the correct answer is ${mealGuessGameUseCase.getCorrectAnswer()}")
        } else giveUserNewAttempt()
    }

    private fun printWinMessage(){
        printLn("Correct!")
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

    private fun getValidGuessFromUser(): Int =
        userInputReader.getValidUserInput(
            { it.toIntOrNull() != null && it.toInt() > 0 },
            message = "Guess no.${mealGuessGameUseCase.getAttemptNumber()}: ",
            invalidInputMessage = "Invalid number."
        ).toInt()

    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)
}
