package logic.mealGuessGame

import logic.MealsDataSource
import model.Meal

class MealGuessGameUseCase(
    private val mealsDataSource: MealsDataSource
) {
    /*** warning: some tests depend on the name of this variable `attemptNumber`, using reflection to control it in test,
     * should you change this name, change it everywhere in `MealGuessGameUseCaseTest` too***/
    private var attemptNumber = 1
    private lateinit var currentMeal: Meal

    fun initGame() {
        attemptNumber = 1
        currentMeal = getValidTimeMeals().random()
    }

    private fun getValidTimeMeals(): List<Meal> {
        return mealsDataSource.getAllMeals()
            .filter(::isMealWithValidTime)
    }

    private fun isMealWithValidTime(meal: Meal): Boolean {
        return meal.minutes != null && meal.minutes > 0
    }

    fun isGamePlayable() = getValidTimeMeals().size >= MIN_MEALS_REQUIRED_TO_START

    fun evaluateGuessAttempt(guess: Int): GuessState {
        attemptNumber++
        return checkGuess(guess, currentMeal.minutes!!)
    }

    private fun checkGuess(guess: Int, correctValue: Int): GuessState {
        return if (guess > correctValue) GuessState.TooHigh
        else if (guess < correctValue) GuessState.TooLow
        else GuessState.Correct
    }

    fun getRandomMealNameWithValidTime(): String = currentMeal.name

    fun isMaxAttemptExceeded(): Boolean = (attemptNumber > MAX_NUMBER_OF_ATTEMPT)

    fun getAttemptNumber(): Int = attemptNumber

    fun getCorrectAnswer(): Int = currentMeal.minutes!!


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