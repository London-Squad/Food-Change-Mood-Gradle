package presentation

import logic.GetIraqiMealsUseCase

class FoodChangeModeConsoleUI(
    //TODO: add feature useCase to class constructor
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase
) {

    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        //TODO: create a function for the new useCase and add case for selecting new feature in 'when' statement (like the first case)

        showOptions()
        val input = getUserInput()
        when (input) {
            1 -> lunchHealthyFoodList()
            3 -> launchGetIraqiMeals()
            0 -> return
            else -> println("Invalid Input")
        }
        presentFeatures()
    }

    private fun showWelcome() {
        println("Welcome to Food Change Mode app")
    }

    private fun showOptions() {
        //TODO: add option for new features

        println("\n\n=== please enter one of the following numbers ===")
        println("1- Get a List of Healthy Fast Food Meals")
        println("3- Get a List of Iraqi Meals")
        println("0- Exit")
        println("here: ")
    }

    private fun lunchHealthyFoodList() {
        //TODO: update this when implementing the useCase
    }

    private fun launchGetIraqiMeals() {
        val iraqiMeals = getIraqiMealsUseCase.getIraqiMeals()
        if (iraqiMeals.isNotEmpty()) {
            iraqiMeals.forEach {
                println(it.name)
            }
        } else {
            println("There is no iraqi meals!")
        }
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}