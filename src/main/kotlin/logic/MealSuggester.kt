package logic

import model.Meal

abstract class MealSuggester(
    private val mealsDataSource: MealsDataSource
) {
    private lateinit var candidateMeals : List<Meal>
    private val suggestedMealIDList = mutableSetOf<Int>()

    protected abstract fun isValidSuggestion(meal: Meal): Boolean

    private fun getMealsFilteredByCondition(
        validatingConditionLambda: (Meal) -> Boolean = { true }
    ): List<Meal> {
        return mealsDataSource.getAllMeals()
            .filter(validatingConditionLambda)
    }

    fun loadSuggestedMealsToMemory() {
        candidateMeals = getMealsFilteredByCondition(::isValidSuggestion)
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

