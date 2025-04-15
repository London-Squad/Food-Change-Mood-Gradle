package presentation

import data.FakeMealsDataSource
import logic.useCase.EasyMealsSuggestionUseCase

class FoodChangeModeConsoleUI(
    //TODO: add feature useCase to class constructor
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
            4 -> startEasyMealView()
            1 -> lunchHealthyFoodList()
            0 -> return
            else -> println("Invalid Input")
        }
        presentFeatures()
    }

    private fun startEasyMealView() {
        //TODO update this when DI with KOIN Applied
        val useCase = EasyMealsSuggestionUseCase(FakeMealsDataSource())
        EasyMealConsoleCLI(useCase).displayEasyMeals()
    }

    private fun showWelcome(){
        println("Welcome to Food Change Mode app")
    }

    private fun showOptions(){
        //TODO: add option for new features

        println("\n\n=== please enter one of the following numbers ===")
        println("4- Get Easy Food Suggestion")
        println("1- Get a List of Healthy Fast Food Meals")
        println("0- Exit")
        println("here: ")
    }

    private fun lunchHealthyFoodList(){
        //TODO: update this when implementing the useCase
    }

    private fun getUserInput(): Int?{
        return readlnOrNull()?.toIntOrNull()
    }
}