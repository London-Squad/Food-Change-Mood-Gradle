package presentation

import logic.KetoMealHelper

class FoodChangeModeConsoleUI(
    private val ketoHelper:KetoMealHelper

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
            7-> suggestOneKetoDishes()
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

    private fun lunchHealthyFoodList(){
        //TODO: update this when implementing the useCase
    }
    private fun suggestOneKetoDishes() {
        val suggestedKetoMeal = ketoHelper.getSuggestedMeal()
        if (suggestedKetoMeal != null) {
            println("\n=== Keto Meal Suggestion ===")
            println(suggestedKetoMeal)
            println("==========================")
        } else {
            println("\nNo more keto meals available!")
            println("Would you like to reset suggestions? (y/n)")
            when (readlnOrNull()?.lowercase()) {
                "y" -> {
                    suggestOneKetoDishes()
                }
                else -> return
            }
        }
    }

    private fun getUserInput(): Int?{
        return readlnOrNull()?.toIntOrNull()
    }
}