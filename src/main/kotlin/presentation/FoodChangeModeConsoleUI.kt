package presentation

import logic.GymHelperUseCase

class FoodChangeModeConsoleUI(
    //TODO: add feature useCase to class constructor
    private val getGymHelperUseCase: GymHelperUseCase
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
            9 -> lunchGymHelper()
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
        println("9- Get a List of Gym Meals")
        println("0- Exit")
        println("here: ")
    }

    private fun lunchHealthyFoodList(){
        //TODO: update this when implementing the useCase
    }

    private fun getUserInput(): Int?{
        return readlnOrNull()?.toIntOrNull()
    }
    private fun lunchGymHelper(){
        getGymHelperUseCase.getGymMembersMeals().forEach {
            println(it)
        }
    }
}