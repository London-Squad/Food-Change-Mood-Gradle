package presentation.foodCulture

import logic.exploreCountryFoodCulture.ExploreCountryFoodCultureUseCase
import logic.util.CountryValidator
import model.Meal
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
        val country = getValidCountryOrNull() ?: return
        country.runCatching(useCase::getMealsOfCountry)
            .onSuccess { mealList -> printMealListOfCountry(mealList, country) }
            .onFailure { throwable -> println(throwable.message ?: "error in displaying meals list") }
    }

    private fun getValidCountryOrNull(): String? {
        val userInput = userInputReader.getValidUserInput(
            { userInput -> (userInput == "0" || CountryValidator.isValidCountryName(userInput)) },
            "Enter country name (or 0 to return to main menu):",
            "Invalid country name"
        )
        return if (userInput == "0") null
        else userInput
    }

    private fun printMealListOfCountry(mealList: List<Meal>, countryName: String) {
        mealList.ifEmpty {
            cliPrinter.cliPrintLn("no meals found :'(")
            null
        }?.also {
            uiMealsListPrinter.printMeals(
                mealList,
                "Explore Country Food Culture: ${countryName.uppercase()}"
            )
        }
    }
}