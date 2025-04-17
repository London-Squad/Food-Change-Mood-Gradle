package presentation

class FoodChangeModeConsoleUI(
    private val getIraqiMealsView: GetIraqiMealsView
) : BaseView {

    override fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()
        when (input) {
            3 -> getIraqiMealsView.start()
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

        println("\n\n=== please enter one of the following numbers ===\n")
        println("3- Get a List of Iraqi Meals")
        println("0- Exit")
        println("here: ")
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}