package presentation.foodCulture

import logic.exploreCountryFoodCulture.ExploreCountryFoodCultureUseCase
import model.Meal
import presentation.BaseView
import presentation.mealDetails.MealListView
import presentation.utils.UserInputReader

class CountryFoodCultureView(
    private val useCase: ExploreCountryFoodCultureUseCase,
    private val userInputReader: UserInputReader
) : BaseView {

    override fun start() {
        printHeader()
        displayCountryFoodList()
    }

    private fun printHeader() {
        println("------------------------------------------")
        println("       Explore Country Food Culture       ")
        println("------------------------------------------")
    }

    private fun displayCountryFoodList() {
        getValidCountryOrNull()
            ?.let(useCase::getMealsOfCountry)
            ?.let {
                it.ifEmpty { println("no meals found :'("); null }
            }
            ?.let { meals: List<Meal> -> MealListView(meals, userInputReader) }
            ?.also(MealListView::start)
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