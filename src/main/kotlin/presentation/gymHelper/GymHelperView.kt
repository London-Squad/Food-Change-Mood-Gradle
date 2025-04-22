package presentation.gymHelper

import logic.gymHelper.GymHelperUseCase
import presentation.BaseView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealsListPrinter
import presentation.utils.UserInputReader

class GymHelperView(
    private val gymHelperUseCase: GymHelperUseCase,
    private val userInputReader: UserInputReader,
    private val uiMealsListPrinter: UIMealsListPrinter,
    private val cliPrinter: CLIPrinter
): BaseView {

    override fun start() {
        val calories = getValidFloatInput("Enter desired calories: ")
        val protein = getValidFloatInput("Enter desired protein: ")
        val approximate = gymHelperUseCase.approximatePercent()

        val matchingMeals = gymHelperUseCase.getGymMembersMeals(calories, protein, approximate)

        if (matchingMeals.isEmpty()) {
            cliPrinter.cliPrintLn("Sorry, no meals match your criteria.\nPress Enter to return to main menu.")
            userInputReader.getUserInput()
            return
        }

        uiMealsListPrinter.printMeals(
            matchingMeals,
            "Gym Meals"
        )
    }

//    private fun getValidFloatInput(message: String): Float =
//        userInputReader.getValidUserInput(
//            ::isValidFloatInput,
//            message,
//            "❌ Invalid input. Please enter a valid number."
//        ).toFloat()

    private fun getValidFloatInput(message: String): Float {
        var validInput = false
        var input: String
        var result: Float? = null

        while (!validInput) {
            input = userInputReader.getValidUserInput(::isValidFloatInput, message, " Invalid input. Please enter a valid number.")
            try {
                result = input.toFloat()
                validInput = result > 0f
            } catch (e: NumberFormatException) {
                cliPrinter.cliPrintLn(" Invalid number format. Please enter a valid number.")
            }
        }
        return result!!
    }


    private fun isValidFloatInput(userInput: String): Boolean{
        val number = userInput.toFloatOrNull()
        return number != null && number > 0
    }
}

