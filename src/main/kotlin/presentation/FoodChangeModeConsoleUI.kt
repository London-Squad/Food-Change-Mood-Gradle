package presentation

class FoodChangeModeConsoleUI(
    //TODO: add feature useCase to class constructor
    private val getAListOfHealthyFoodMealsUseCase:Int   //TODO: update the type when implementing the useCase
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
        println("1- get a List of Healthy Fast Food Meals")
        println("here: ")
    }

    private fun lunchHealthyFoodList(){

        //TODO: update this when implementing the useCase

        println(getAListOfHealthyFoodMealsUseCase)
    }

    private fun getUserInput(): Int?{
        return readlnOrNull()?.toIntOrNull()
    }
}