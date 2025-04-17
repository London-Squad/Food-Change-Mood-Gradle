package logic

import model.Meal

class KetoMealHelper(private val mealRepository: MealsDataSource){
    private val suggestedMealIds = mutableSetOf<Int>()
    private val validator = KetoFriendlyValidator()
    fun getSuggestedMeal(): Meal? {
        val unsuggestedMeals = mealRepository.getAllMeals()
            .filter { !suggestedMealIds.contains(it.id) }

        val nextKetoSuggestedMeal = unsuggestedMeals.firstOrNull { validator.isKetoFriendly(it) }

        return nextKetoSuggestedMeal?.also { meal ->
            suggestedMealIds.add(meal.id)
        }
    }
}