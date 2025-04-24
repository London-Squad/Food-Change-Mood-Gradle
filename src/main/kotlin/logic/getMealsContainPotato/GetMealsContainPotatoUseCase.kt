package logic.getMealsContainPotato

import logic.MealsDataSource
import model.Meal
import kotlin.random.Random

class GetMealsContainPotatoUseCase(
    private val mealsDataSource: MealsDataSource
) {

    fun getRandomMealsWithPotato(): List<Meal> {
        val potatoMeals = filterMealsWithPotato(mealsDataSource.getAllMeals())
        return selectRandomMeals(potatoMeals, NUMBER_OF_MEALS_TO_PRESENT)
    }

    //  Filtering logic separated
    private fun filterMealsWithPotato(meals: List<Meal>): List<Meal> {
        return meals.filter { meal ->
            meal.tags.any { it.equals(POTATOES_TAG, ignoreCase = true) } ||
                    meal.ingredients.any { it.contains(POTATO_KEYWORD, ignoreCase = true) }
        }
    }

    // Random selection using index-based selection instead of .shuffled()
    private fun selectRandomMeals(meals: List<Meal>, count: Int): List<Meal> {
        if (meals.size <= count) return meals

        val selectedIndices = mutableSetOf<Int>()
        while (selectedIndices.size < count) {
            selectedIndices.add(Random.nextInt(meals.size))
        }
        return selectedIndices.map { meals[it] }
    }

    private companion object {
        const val POTATOES_TAG = "potatoes"
        const val POTATO_KEYWORD = "potato"
        const val NUMBER_OF_MEALS_TO_PRESENT = 10
    }
}
