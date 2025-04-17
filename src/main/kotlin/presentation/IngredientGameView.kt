package presentation

import logic.IngredientGameUseCase
import model.Meal

class IngredientGameView(
    private val ingredientGameUseCase: IngredientGameUseCase
) : BaseView {

    override fun start() {
        printHeader()
        printRules()
        ingredientGameUseCase.resetGame()
        startGame()
    }

    private fun startGame() {
        startRound()
        if (ingredientGameUseCase.isWin()) {
            println("You Win!")
            println("your points are: ${ingredientGameUseCase.getScore()}")
        } else if (ingredientGameUseCase.isLoss()) {
            println("------------------------------------------")
            println("                 Game Over                ")
            println("your points are: ${ingredientGameUseCase.getScore()}")
            println("------------------------------------------")
        } else {
            println("correct!")
            println("-----------------------")
            startGame()
        }
    }

    private fun startRound() {
        ingredientGameUseCase.apply {
            getMealAndIngredientOptions()
                .also(::printMealAndIngredientOptions)
                .also {
                    evaluateChoice(it.first, it.second, getValidGuessFromUser())
                }
        }
    }

    private fun printMealAndIngredientOptions(pair: Pair<Meal, List<String>>) {
        println("\nMeal Name: ${pair.first.name}\n")
        pair.second.forEachIndexed { index, ingredient ->
            println("${index + 1}. $ingredient")
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
        println("2. A correct guess  you 1000 points.")
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

    private companion object {
    }
}