package logic.getHighCalorieMeals

import logic.MealSuggester
import logic.MealsDataSource
import model.Meal

class GetHighCalorieMealsUseCase(mealsDataSource: MealsDataSource) : MealSuggester(mealsDataSource) {

    override fun isValidSuggestion(meal: Meal): Boolean =
        meal.nutrition.calories?.let { it > 700f } ?: false

}
