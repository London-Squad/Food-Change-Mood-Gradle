package logic.mealGuessGame

import logic.MealsDataSource
import model.Meal

class MealGuessGameUseCase(
    private val mealsDataSource: MealsDataSource
) {

    fun getRandomMeal(): Meal {
        return getValidTimeMeals().random()
    }

    private fun getValidTimeMeals(): List<Meal> {
        return mealsDataSource.getAllMeals()
            .filter(::isMealWithValidTime)
    }

    fun isGamePlayable() = getValidTimeMeals().size >= MIN_MEALS_REQUIRED_TO_START

    fun checkGuess(guess: Int, correctValue: Int): GuessState {
        return if (guess > correctValue) GuessState.TooHigh
        else if (guess < correctValue) GuessState.TooLow
        else GuessState.Correct
    }

    fun isAttemptExceeded(currentAttempt: Int): Boolean = (currentAttempt > MAX_NUMBER_OF_ATTEMPT)


    private fun isMealWithValidTime(meal: Meal): Boolean {
        return meal.minutes != null && meal.minutes > 0
    }

    enum class GuessState(val state: String) {
        Correct("Correct"),
        TooHigh("Too High"),
        TooLow("Too Low")
    }

    private companion object {
        const val MAX_NUMBER_OF_ATTEMPT = 3
        const val MIN_MEALS_REQUIRED_TO_START = 3
    }
}