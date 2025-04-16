package presentation

import logic.GetIraqiMealsUseCase

class GetIraqiMealsView(private val getIraqiMealsUseCase: GetIraqiMealsUseCase) : BaseView {
    override fun start() {
        val iraqiMeals = getIraqiMealsUseCase.getIraqiMeals()
        if (iraqiMeals.isNotEmpty()) {
            iraqiMeals.forEach {
                println(it.name)
            }
        } else {
            println("There is no iraqi meals!")
        }
    }
}