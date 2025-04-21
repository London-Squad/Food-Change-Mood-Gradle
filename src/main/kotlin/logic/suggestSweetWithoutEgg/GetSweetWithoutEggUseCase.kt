package logic.suggestSweetWithoutEgg

import logic.MealSuggester
import logic.MealsDataSource
import model.Meal

class GetSweetWithoutEggUseCase(mealsDataSource: MealsDataSource) : MealSuggester(mealsDataSource) {

    fun initSuggestions() {
        initSuggestedList()
    }

    fun suggestSweet(): Meal? {
        return suggestMeal()
    }

    override fun isValidSuggestion(meal: Meal): Boolean {
        return isSweetWithoutEgg(meal)
    }

    private fun isSweetWithoutEgg(meal: Meal): Boolean {
        meal.apply {
            val isEggFree =
                (!(ingredients.contains("egg") || ingredients.contains("eggs")) || tags.contains("egg-free"))
            val isSweet = tags.contains("sweet")

            return isSweet && isEggFree
        }
    }
}