package presentation

class FoodChangeModeConsoleUI(
    //TODO: add feature useCase to class constructor
    private val mealGuessGameView: MealGuessGameView
) {

    fun start(){
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures(){
        //TODO: create a function for the new useCase and add case for selecting new feature in 'when' statement (like the first case)

        showOptions()
        val input = getUserInput()
        when(input){
            1 -> lunchHealthyFoodList()
            5 -> lunchMealGuessGame()
            0 -> return
            else -> println("Invalid Input")
        }
        presentFeatures()
    }

    private fun showWelcome(){
        println("Welcome to Food Change Mode app")
    }

    private fun showOptions(){
        //TODO: add option for new features

        println("\n\n=== please enter one of the following numbers ===")
        println("1- Get a List of Healthy Fast Food Meals")
        println("5- Meal Guess Game")
        println("0- Exit")
        println("here: ")
    }

    private fun lunchHealthyFoodList(){
        //TODO: update this when implementing the useCase
    }
    private fun lunchMealGuessGame(){
        mealGuessGameView.displayGame()
    }

    private fun getUserInput(): Int?{
        return readlnOrNull()?.toIntOrNull()
    }
}