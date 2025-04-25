package logic.easyMealsSuggestion

import logic.MealsDataSource
import logic.util.getRandomMeals
import model.Meal

class EasyMealsSuggestionUseCase(
    private val mealsDataSource: MealsDataSource
) {
    fun getRandomMeals(count: Int = 10): List<Meal> {
        return mealsDataSource.getAllMeals()
            .filter(::isEasyMeal)
            .getRandomMeals(count)
    }

    private fun isEasyMeal(meal: Meal): Boolean {
        return meal.minutes?.let { it <= MIN_MIN } == true
                && meal.ingredients.size <= MIN_NUMBER_OF_INGREDIENTS
                && meal.steps.size <= MIN_NUMBER_OF_STEPS
    }

    companion object {
        private const val MIN_MIN = 30
        private const val MIN_NUMBER_OF_INGREDIENTS = 5
        private const val MIN_NUMBER_OF_STEPS = 6
    }
}