package logic

import model.Meal
import kotlin.random.Random

class IngredientGameUseCase(
    private val mealsDataSource: MealsDataSource
) {
    private var score: Int = 0
    private var loss: Boolean = false

    fun getScore() = score

    fun getMealAndIngredientOptions(): Pair<Meal, List<String>> =
        getRandomMeal().run {
            Pair(
                this,
                listOf(
                    getCorrectIngredient(),
                    getIncorrectIngredient(),
                    getIncorrectIngredient()
                ).shuffled()
            )
        }

    fun resetGame() {
        score = 0
        loss = false
    }

    private fun getRandomMeal(): Meal = mealsDataSource.getAllMeals().random()

    private fun Meal.getCorrectIngredient(): String =
        ingredients[Random.nextInt(0, ingredients.size)]

    fun isWin() = score >= MAX_POINTS
    fun isLoss() = loss

    fun evaluateChoice(meal: Meal, ingredientOptions: List<String>, choice: Int) {
        if (isCorrect(meal, ingredientOptions[choice - 1])) {
            score += POINTS_PER_CORRECT_ANSWER
        } else {
            loss = true
        }
    }

    private fun Meal.getIncorrectIngredient(
        numberOfAttempts: Int = DEFAULT_MAX_NUMBER_OF_ATTEMPTS_TO_FIND_INVALID_INGREDIENT
    ): String =
        if (numberOfAttempts <= 0) {
            ""
        } else {
            getRandomMeal().let { anotherMeal ->
                anotherMeal.ingredients.firstOrNull { incorrectIngredient ->
                    ingredients.all { incorrectIngredient !in it }
                } ?: getIncorrectIngredient(numberOfAttempts - 1)
            }
        }

    private fun isCorrect(meal: Meal, ingredient: String): Boolean =
        meal.ingredients.any { it == ingredient }

    private companion object {
        const val DEFAULT_MAX_NUMBER_OF_ATTEMPTS_TO_FIND_INVALID_INGREDIENT = 10
        const val MAX_POINTS = 15000
        const val POINTS_PER_CORRECT_ANSWER = 1000

    }
}