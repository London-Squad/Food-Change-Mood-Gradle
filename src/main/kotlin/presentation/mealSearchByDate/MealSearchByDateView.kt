package presentation.mealSearchByDate

import logic.search.MealSearchUseCase
import presentation.BaseView
import logic.util.InvalidDateFormatException
import logic.util.NoMealsFoundException
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader

class MealSearchByDateView(
    private val mealSearchUseCase: MealSearchUseCase<List<Pair<Int, String>>>,
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter,
    private val uiMealPrinter: UIMealPrinter,
    private val uiMealsListPrinterAndSelectByIDTemp: UIMealsListPrinterAndSelectByID
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

        uiMealsListPrinterAndSelectByIDTemp.printMeals(
            dateInput,
            searchResults,
            mealSearchUseCase
        )

    }
}