package presentation.ingredientGame

import logic.ingredientGame.IngredientGameUseCase
import presentation.BaseView
import presentation.utils.CLIPrinter
import presentation.utils.UserInputReader

class IngredientGameView(
    private val ingredientGameUseCase: IngredientGameUseCase,
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter
) : BaseView {

    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)

    override fun start() {
        if (!ingredientGameUseCase.isGamePlayable()) {
            printLn("can't play the game :'("); return
        }
        prepareGame()
        startNewRound()
    }

    private fun prepareGame() {
        printHeader()
        printRules()
        ingredientGameUseCase.resetGame()
    }

    private fun startNewRound() {
        printRandomMealNameAndIngredientOptions()

        ingredientGameUseCase.apply {
            evaluateChoice(getValidGuessFromUser())
            when {
                isChoiceWrong() -> printLossMessage()
                isAllRoundsFinished() -> printWinMessage()
                else -> {
                    printCorrectChoiceMessage(); startNewRound()
                }
            }
        }
    }

    private fun printLossMessage() {
        printLn("Incorrect Choice")
        printLn("------------------------------------------")
        printLn("                 Game Over                ")
        printLn("your points are: ${ingredientGameUseCase.getScore()}")
        printLn("------------------------------------------")
    }

    private fun printWinMessage() {
        printLn("You Win!")
        printLn("your points are: ${ingredientGameUseCase.getScore()}")
    }

    private fun printCorrectChoiceMessage() {
        printLn("correct!")
        printLn("-----------------------")
    }

    private fun printRandomMealNameAndIngredientOptions() {
        ingredientGameUseCase.getRandomMealNameAndIngredientOptions()
            .apply {
                printLn("\nMeal Name: ${first}\n")
                second.forEachIndexed { index, ingredient ->
                    printLn("${index + 1}. $ingredient")
                }
            }
    }

    private fun printHeader() {
        printLn("------------------------------------------")
        printLn("             Ingredient Game              ")
        printLn("------------------------------------------")
    }

    private fun printRules() {
        printLn("Rules: ")
        printLn("1. given a meal name and three ingredient, guess which ingredient is used for that meal.")
        printLn("2. A correct guess gives you a 1000 points.")
        printLn("3. An incorrect guess ends the game")
        printLn("4. The game also ends after 15 correct answers\n")
    }

    private fun getValidGuessFromUser(): Int =
        userInputReader.getValidUserInput(
            {it in listOf("1", "2", "3")},
            message = "Your Choice: ",
            invalidInputMessage = "Invalid Choice, Please input a 1, 2, or 3."
        ).toInt()
}