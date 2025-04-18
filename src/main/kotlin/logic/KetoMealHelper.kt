package logic

import model.Meal

class KetoMealHelper(
    private val mealRepository: MealsDataSource,
    private val validator: KetoFriendlyValidator
) {
    private val suggestedMealIds = mutableSetOf<Int>()

    fun getKetoDishesSuggestion(): Meal? {
        val allMeals = mealRepository.getAllMeals()

        if (allMeals.isEmpty()) return null

        val candidates = allMeals.filter {
            validator.isKetoFriendly(it) && !suggestedMealIds.contains(it.id)
        }

        val finalCandidates = candidates.ifEmpty {
            suggestedMealIds.clear()
            allMeals.filter { validator.isKetoFriendly(it) }
        }

        return finalCandidates.random().also {
            suggestedMealIds.add(it.id)
        }
    }
}
