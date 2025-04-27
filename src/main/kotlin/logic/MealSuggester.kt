package logic

import model.Meal

abstract class MealSuggester(
    private val mealsDataSource: MealsDataSource
) {
    private lateinit var candidateMeals: MutableList<Meal>

    protected abstract fun isValidSuggestion(meal: Meal): Boolean

    private fun getMealsFilteredByCondition(
        validatingConditionLambda: (Meal) -> Boolean)
    : List<Meal> {
        return mealsDataSource.getAllMeals()
            .filter(validatingConditionLambda)
    }

    fun loadSuggestedMealsToMemory() {
        candidateMeals = getMealsFilteredByCondition(::isValidSuggestion).toMutableList()
    }

    fun suggestMeal(): Meal? {

        if (candidateMeals.isEmpty()) return null

        val suggestion = candidateMeals.random()
        candidateMeals.remove(suggestion)
        return suggestion
    }
}

