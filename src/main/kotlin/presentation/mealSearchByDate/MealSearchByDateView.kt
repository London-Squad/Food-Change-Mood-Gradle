package presentation.mealSearchByDate

import logic.search.MealSearchUseCase
import logic.search.byDate.MealSearchByDateUseCaseImpl
import presentation.BaseView
import logic.util.InvalidDateFormatException
import logic.util.NoMealsFoundException
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UIMealsListPrinter
import presentation.utils.UserInputReader

class MealSearchByDateView(
    private val mealSearchUseCase: MealSearchUseCase<List<Pair<Int, String>>>,
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter,
    private val uiMealPrinter: UIMealPrinter,
    private val uiMealsListPrinter: UIMealsListPrinter
) : BaseView {

    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)

    override fun start() {
        uiMealPrinter.printTextWithinWidth("Enter a date to search for meals (yyyy-MM-dd, e.g., 2023-04-16) or '0' to return to main menu:")
        val dateInput = userInputReader.getUserInput()
        if (dateInput == "0") return

        val searchResults = try {
            mealSearchUseCase.searchMeals(dateInput)
        } catch (e: InvalidDateFormatException) {
            printLn("Error: ${e.message}")
            return
        } catch (e: NoMealsFoundException) {
            printLn("Error: ${e.message}")
            return
        }

        if (searchResults.isEmpty()) {
            printLn("No meals found for date '$dateInput'.")
            return
        }

        val meals = searchResults.mapNotNull { (id, _) ->
            try {
                (mealSearchUseCase as MealSearchByDateUseCaseImpl).getMealDetails(id)
            } catch (e: NoMealsFoundException) {
                printLn("Error: ${e.message}")
                null
            }
        }

        if (meals.isEmpty()) {
            printLn("No meals could be retrieved for date '$dateInput'.")
            return
        }

        uiMealsListPrinter.printMeals(
            meals,
            "Meals Added on '$dateInput'",
            mealTextInList = { meal -> "${meal.name} (ID: ${meal.id})" })
    }
}