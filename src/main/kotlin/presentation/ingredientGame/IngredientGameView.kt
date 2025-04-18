package presentation.ingredientGame

import logic.ingredientGame.IngredientGameUseCase
import presentation.BaseView

class IngredientGameView(
    private val ingredientGameUseCase: IngredientGameUseCase
) : BaseView {

    override fun start() {
        if (!ingredientGameUseCase.isGamePlayable()) {println("can't play the game :'("); return}
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
        println("Incorrect Choice")
        println("------------------------------------------")
        println("                 Game Over                ")
        println("your points are: ${ingredientGameUseCase.getScore()}")
        println("------------------------------------------")
    }

    private fun printWinMessage() {
        println("You Win!")
        println("your points are: ${ingredientGameUseCase.getScore()}")
    }

    private fun printCorrectChoiceMessage() {
        println("correct!")
        println("-----------------------")
    }

    private fun printRandomMealNameAndIngredientOptions() {
        ingredientGameUseCase.apply {
            getRandomMealNameAndIngredientOptions()
                .apply {
                    println("\nMeal Name: ${first}\n")
                    second.forEachIndexed { index, ingredient ->
                        println("${index + 1}. $ingredient")
                    }
                }
        }
    }

    private fun printHeader() {
        println("------------------------------------------")
        println("             Ingredient Game              ")
        println("------------------------------------------")
    }

    private fun printRules() {
        println("Rules: ")
        println("1. given a meal name and three ingredient, guess which ingredient is used for that meal.")
        println("2. A correct guess gives you a 1000 points.")
        println("3. An incorrect guess ends the game")
        println("4. The game also ends after 15 correct answers\n")
    }

    private fun getValidGuessFromUser(): Int {
        print("your choice: ")
        val guessInput = readln().trim()
        return if (guessInput in listOf("1", "2", "3"))
            guessInput.toInt()
        else {
            println("invalid input")
            getValidGuessFromUser()
        }
    }
}