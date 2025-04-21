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
        getCountryFromUser()
            ?.let(useCase::getMealsOfCountry)
            ?.let{
                if (it.isEmpty()) {println("no meals found :'("); null} else it
            }
            ?.let{meals: List<Meal> -> MealListView(meals, userInputReader)}
            ?.also(MealListView::start)
    }

    private fun getCountryFromUser(): String? {
        val userInput = userInputReader.getUserInput("Enter country name (or 0 to return to main menu) : ")
        if (userInput == "0") {
            return null
        }
        return if (useCase.isCountry(userInput)) {
            userInput
        } else {
            println("Enter Valid country name: ")
            getCountryFromUser()
        }
    }

}