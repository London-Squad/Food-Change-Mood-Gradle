package presentation

import logic.SweetSuggester


class FoodChangeModeConsoleUI(
    //TODO: add feature useCase to class constructor
    private val sweetSuggester: SweetSuggester
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
            2 -> runSweetsWithoutEggsFeature()
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
        println("2- Suggest Sweets Without Eggs")
        println("0- Exit")
        println("here: ")
    }

    private fun lunchHealthyFoodList(){
        //TODO: update this when implementing the useCase
    }

    private fun runSweetsWithoutEggsFeature() {
        println("Sweets Without Eggs")

        var sweet = sweetSuggester.suggestSweet()
        while (sweet != null) {
            println("\nTry this sweet: ${sweet.name}")
            println("Description: ${sweet.description}")

            println("Do you like it? (y = like, n = dislike, x = exit): ")
            when (readlnOrNull()?.lowercase()) {
                "y" -> {
                    sweetSuggester.likeSweet(sweet)
                    break
                }
                "n" -> {
                    sweet = sweetSuggester.dislikeSweet()
                }
                "x" -> {
                    println("Returning to main menu...")
                    return
                }
                else -> println("Invalid input. Try again.")
            }
        }

        if (sweet == null) {
            println("No more sweets to suggest!")
        }
    }

    private fun getUserInput(): Int?{
        return readlnOrNull()?.toIntOrNull()
    }
}