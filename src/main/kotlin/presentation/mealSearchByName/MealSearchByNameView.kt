package presentation.mealSearchByName

import logic.search.MealSearchUseCase
import model.Meal
import presentation.BaseView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealsListPrinter
import presentation.utils.UserInputReader

class MealSearchByNameView(
    private val mealSearchUseCase: MealSearchUseCase<List<Meal>>,
    private val userInputReader: UserInputReader,
    private val cliPrinter: CLIPrinter,
    private val uiMealsListPrinter: UIMealsListPrinter
): BaseView {

    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)

    override fun start() {
        val keyword =
            userInputReader.getUserInput("Enter a keyword to search for meals (or '0' to return to main menu): ")

        if (keyword == "0") return

        val searchResults = mealSearchUseCase.searchMeals(keyword)

        if (searchResults.isEmpty()) {
            printLn("No meals found for keyword '$keyword'.")
            return
        }

        uiMealsListPrinter.printMeals(searchResults, "Search Results")
    }
}