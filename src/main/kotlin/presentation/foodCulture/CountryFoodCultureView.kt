package presentation.foodCulture

import logic.useCase.ExploreCountryFoodCultureUseCase
import presentation.BaseView
import presentation.meal.MealListView

class CountryFoodCultureView(
    private val useCase: ExploreCountryFoodCultureUseCase
) : BaseView {

    override fun start() {
        printHeader()
        getCountryFromUserAndPrintFoodList()
    }

    private fun printHeader() {
        println("------------------------------------------")
        println("       Explore Country Food Culture       ")
        println("------------------------------------------")
    }

    private fun getCountryFromUserAndPrintFoodList() {
        getCountryFromUser()
            ?.let {
                if (ExploreCountryFoodCultureUseCase.countries.any { country -> country.equals(it, ignoreCase = true) })
                    it
                else
                    "No Such Country Name"
            }
            ?.let(useCase::exploreCountryFoodCulture)
            ?.run(::MealListView)
            ?.also(MealListView::start)
            ?: println("We don't have data about that country\n")
    }

    private fun getCountryFromUser(): String? {
        print("enter country name: ")
        return readlnOrNull()
    }
}