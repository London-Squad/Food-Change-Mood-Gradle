package logic

import model.Meal

abstract class MealSuggester(
    private val mealsDataSource: MealsDataSource
) {
    private val candidateMeals = getMealsFilteredByCondition(::isValidSuggestion)
    private val suggestedMealIDList = mutableSetOf<Int>()

    abstract protected fun isValidSuggestion(meal: Meal): Boolean

    private fun getMealsFilteredByCondition(
        validatingConditionLambda: (Meal) -> Boolean = { true }
    ): List<Meal> {
        return mealsDataSource.getAllMeals()
            .filter(validatingConditionLambda)
    }

    fun suggestMeal(): Meal? {

        if (candidateMeals.isEmpty()) return null
        if (candidateMeals.size == suggestedMealIDList.size) return null

        do {
            val suggestion = candidateMeals.random().takeIf { suggestedMealIDList.add(it.id) }
            if (suggestion != null) return suggestion
        } while (true)
    }

    fun initSuggestedList() {
        suggestedMealIDList.clear()
    }
}

