package presentation

import logic.GetHealthyFastFoodMeals

class FoodChangeModeConsoleUI(
    private val getHealthyFastFoodMeals : GetHealthyFastFoodMeals
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
            1 -> launchHealthyFoodList()
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
        println("0- Exit")
        println("here: ")
    }

    private fun launchHealthyFoodList() {
        val healthyMeals = getHealthyFastFoodMeals.getHealthyFastFoodMeals()

        println("🍽️ Healthy Fast Food Meals (ready in 15 minutes or less):")
        healthyMeals.forEach { meal ->
            println("• Meal: ${meal.name} | Preparation Time: ${meal.minutes} minutes")
        }
    }


    private fun getUserInput(): Int?{
        return readlnOrNull()?.toIntOrNull()
    }
}