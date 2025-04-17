package logic

import model.Meal
import kotlin.math.min

class KetoMealHelper(private val mealRepository: MealsDataSource) {
    private val suggestedMealIds = mutableSetOf<Int>()
    private val validator = KetoFriendlyValidator()

    fun getKetoDishesSuggestion(): Meal {
        val allMeals = mealRepository.getAllMeals()

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
