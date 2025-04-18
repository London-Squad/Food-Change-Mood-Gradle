package logic

import model.Meal
import kotlin.properties.Delegates

class IngredientGameUseCase(
    private val mealsDataSource: MealsDataSource
) {

    private var score: Int = 0
    private var loss: Boolean = false
    private var correctChoice by Delegates.notNull<Int>()

    fun isGamePlayable(): Boolean = mealsDataSource.getAllMeals().size > 2

    fun getScore() = score

    fun getRandomMealNameAndIngredientOptions(): Pair<String, List<String>> =
        getRandomMeal().run {
            Pair(
                name,
                generateIngredientOptions(ingredients)
                    .also { options -> correctChoice = 1 + options.indexOfFirst { it in ingredients } }
            )
        }

    private fun getRandomMeal(): Meal = mealsDataSource.getAllMeals().random()

    private fun generateIngredientOptions(ingredients: List<String>): List<String> =
        listOf(
            ingredients.random(),
            getIngredientNotUsedInMeal(ingredients),
            getIngredientNotUsedInMeal(ingredients)
        ).shuffled()

    fun resetGame() {
        score = 0
        loss = false
    }

    fun isAllRoundsFinished() = (score >= MAX_POINTS)
    fun isChoiceWrong() = loss

    fun evaluateChoice(choice: Int) {
        if (choice == correctChoice) {
            score += POINTS_PER_CORRECT_ANSWER
        } else {
            loss = true
        }
    }

    private fun getIngredientNotUsedInMeal(
        excludedIngredients: List<String>,
        numberOfAttempts: Int = DEFAULT_MAX_NUMBER_OF_ATTEMPTS_TO_FIND_INVALID_INGREDIENT
    ): String =
        "".takeIf { numberOfAttempts <= 0 } ?: getRandomMeal().run {
            ingredients.firstOrNull { ingredient -> ingredient !in excludedIngredients } ?: getIngredientNotUsedInMeal(
                excludedIngredients,
                numberOfAttempts - 1
            )
        }

    private companion object {
        const val DEFAULT_MAX_NUMBER_OF_ATTEMPTS_TO_FIND_INVALID_INGREDIENT = 10
        const val MAX_POINTS = 15000
        const val POINTS_PER_CORRECT_ANSWER = 1000

    }
}