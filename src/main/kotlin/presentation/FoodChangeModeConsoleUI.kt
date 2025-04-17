package presentation

class FoodChangeModeConsoleUI(
    private val getIraqiMealsView: GetIraqiMealsView,
    private val mealGuessGameView: MealGuessGameView,
    private val sweetSuggester: SweetSuggester
) : BaseView {

    override fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()
        when (input) {
            2 -> runSweetsWithoutEggsFeature()
            3 -> getIraqiMealsView.start()
            5 -> mealGuessGameView.start()
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
        println("2- Suggest Sweets Without Eggs")
        println("3- Get a List of Iraqi Meals")
        println("5- Meal Guess Game")
        println("0- Exit")
        println("here: ")
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