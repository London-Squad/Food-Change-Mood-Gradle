package presentation.foodCulture

import logic.useCase.ExploreCountryFoodCultureUseCase
import presentation.BaseView
import presentation.meal.MealListView

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

    private fun validateCountryInput(): String? {
        return getCountryFromUser()?.let {
            if (ExploreCountryFoodCultureUseCase.countries.any { country -> country.equals(it, ignoreCase = true) })
                it
            else
                "No Such Country Name"
        }
    }

    private fun displayCountryFoodList() {
        validateCountryInput()
            ?.let(useCase::getMealsOfCountry)
            ?.run(::MealListView)
            ?.also(MealListView::start)
            ?: println("We don't have data about that country\n")
    }

    private fun getCountryFromUser(): String? {
        print("enter country name: ")
        return readlnOrNull()
    }
}