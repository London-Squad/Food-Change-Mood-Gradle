package presentation.foodCulture

import logic.exploreCountryFoodCulture.ExploreCountryFoodCultureUseCase
import presentation.BaseView
import presentation.mealDetails.MealListView

class CountryFoodCultureView(
    private val useCase: ExploreCountryFoodCultureUseCase
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
            ?.run(::MealListView)
            ?.also(MealListView::start)
    }

    private fun getCountryFromUser(): String? {
        print("Enter country name (or 0 to return to main menu) : ")
        val userInput = readln()
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