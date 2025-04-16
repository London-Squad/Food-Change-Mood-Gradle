package logic

import model.Meal

class MealGuessGameUseCase(
    private val mealsDataSource: MealsDataSource
) {

    fun startGuessGame(){

        while(true){

            println("Enter anything to continue or 0 to to exit: ")

            if (readlnOrNull()=="0") return

            val randomMeal = getRandomMeal()

            println(randomMeal.name)
            for (attempt in 1..3){
                val guess = getValidGuess(attempt)
                val result = checkGuessAttempt(guess, randomMeal.minutes!!)
                println(result)
                if (result == "correct") return
            }
            println("Game Over! the correct answer is ${randomMeal.minutes}")
        }

    }
    private fun getRandomMeal(): Meal {
        return mealsDataSource.getAllMeals()
            .getRandomMeals(::isMealWithValidTime, 1).first()
    }

    private fun checkGuessAttempt(guess: Int, correctValue: Int): String{
        return if (guess > correctValue) "Too high"
        else if (guess < correctValue) "Too low"
        else "Correct!"
    }

    private fun getValidGuess(attempt: Int): Int{
        while (true){
            println("Guess no.$attempt: ")
            val guessInput = readlnOrNull()?.toIntOrNull()
            if (guessInput == null)
                println("invalid input")
            else {
                return guessInput
            }
        }

    }
    private fun isMealWithValidTime(meal: Meal): Boolean {
        return meal.minutes != null
    }

    private fun List<Meal>.getRandomMeals(condition: (Meal) -> Boolean, count: Int): List<Meal> {
        return this.filter(condition).run {
            if (size < count) this
            else shuffled().slice(0..<count)
        }
    }
}