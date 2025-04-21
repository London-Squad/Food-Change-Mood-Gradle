package presentation.foodCulture

import logic.exploreCountryFoodCulture.ExploreCountryFoodCultureUseCase
import presentation.BaseView
import presentation.utils.CLIPrinter
import presentation.utils.UIMealsListPrinter
import presentation.utils.UserInputReader

class CountryFoodCultureView(
    private val useCase: ExploreCountryFoodCultureUseCase,
    private val userInputReader: UserInputReader,
    private val uiMealsListPrinter: UIMealsListPrinter,
    private val cliPrinter: CLIPrinter
    ) : BaseView {

    override fun start() {
        displayCountryFoodList()
    }

    private fun displayCountryFoodList() {
        val country = getValidCountryOrNull()

        country?.let(useCase::getMealsOfCountry)
            ?.let {
                it.ifEmpty { cliPrinter.cliPrintLn("no meals found :'("); null }
            }
            ?.also { meals -> uiMealsListPrinter.printMeals(meals, "Explore Country Food Culture: ${country.uppercase()}") }
    }

    private fun getValidCountryOrNull(): String? {
        val userInput = userInputReader.getValidUserInput(
            { userInput -> (userInput == "0" || useCase.isCountry(userInput)) },
            "Enter country name (or 0 to return to main menu):",
            "Invalid country name"
        )
        return if (userInput == "0") null
        else userInput
    }
}