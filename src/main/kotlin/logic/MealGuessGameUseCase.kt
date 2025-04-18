package logic

import model.Meal

class MealGuessGameUseCase(
    private val mealsDataSource: MealsDataSource
) {

    fun getRandomMeal(): Meal {
        return mealsDataSource.getAllMeals()
            .getRandomMeals(::isMealWithValidTime, 1).first()
    }

    fun isTwoOrMoreMealsAvailable() = mealsDataSource.getAllMeals().size > 2

    fun checkGuessAttempt(guess: Int, correctValue: Int): String {
        return if (guess > correctValue) "Too high"
        else if (guess < correctValue) "Too low"
        else "correct"
    }

    fun isAttemptExceeded(currentAttempt: Int): Boolean = (currentAttempt > MAX_NUMBER_OF_ATTEMPT)


    private fun isMealWithValidTime(meal: Meal): Boolean {
        return meal.minutes != null
    }

    private fun List<Meal>.getRandomMeals(condition: (Meal) -> Boolean, count: Int): List<Meal> {
        return this.filter(condition).run {
            if (size < count) this
            else shuffled().slice(0..<count)
        }
    }

    private companion object {
        const val MAX_NUMBER_OF_ATTEMPT = 3
    }
}