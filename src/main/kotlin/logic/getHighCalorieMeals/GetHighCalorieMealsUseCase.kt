package logic.getHighCalorieMeals

import logic.MealSuggester
import logic.MealsDataSource
import model.Meal

class GetHighCalorieMealsUseCase(mealsDataSource: MealsDataSource) : MealSuggester(mealsDataSource) {

    fun initSuggestions() {
        initSuggestedList()
    }

    fun suggestHighCalorieMeal(): Meal? {
        return suggestMeal()
    }

    override fun isValidSuggestion(meal: Meal): Boolean {
        return isHighCalorieMeal(meal)
    }

    private fun isHighCalorieMeal(meal: Meal): Boolean =
        meal.nutrition.calories?.let { it > 700f } ?: false

}
