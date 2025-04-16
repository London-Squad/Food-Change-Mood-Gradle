package logic

import data.FileReader
import data.csvData.CsvMealsDataSourceOneTimeLoad
import data.csvData.CsvMealsParser
import model.Meal
import java.io.File

fun main(){
    val csvFile = File("food.csv")
    val fileReader = FileReader(csvFile)
    val csvMealsParser = CsvMealsParser()

//    val mealsDataSource = FakeMealsDataSource()
//    val mealsDataSource = CsvMealsDataSource(fileReader, csvMealsParser)
    val mealsDataSource = CsvMealsDataSourceOneTimeLoad(fileReader, csvMealsParser, numberOfMealsToBeLoaded = 50)

    val game = MealQuessGameUseCase(mealsDataSource)
    game.startGuessGame()
}

class MealQuessGameUseCase(
    private val mealsDataSource: MealsDataSource
) {

    fun startGuessGame(){

        printHeader()
        printRules()

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

    private fun printHeader(){
        println("------------------------------------------")
        println("             Meal Guess Game              ")
        println("------------------------------------------")
    }
    private fun printRules(){
        println("Rules: ")
        println("1. A random meal will be presented and you have to guess the time needed to prepare it.")
        println("2. You have 3 attempts only.")
        println("3. If the guess is incorrect, there will be hint for next attempt.\n")
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