package logic

import model.Meal

class SuggestSweetWithoutEggUseCase(mealsDataSource: MealsDataSource) {
    private val eggFreeSweets = getSweetsWithoutEggs(mealsDataSource.getAllMeals())
    private val suggested = mutableSetOf<Int>()

    fun suggestSweet(): Meal? {
        if (eggFreeSweets.isEmpty()) return null

        val suggestion = eggFreeSweets.shuffled().firstOrNull { it.id !in suggested }
            ?.apply { suggested.add(id) }

        return suggestion
    }

    fun clearSuggestedList() {
        suggested.clear()
    }

    private fun getSweetsWithoutEggs(meals: List<Meal>): List<Meal> {
        return meals.filter { meal ->
            val nameLower = meal.name.lowercase()
            val ingredientsLower = meal.ingredients.joinToString(",")
            val isSweet =
                listOf("cake", "cookie", "dessert", "sweet", "brownie", "pie", "pudding").any { it in nameLower }
            val containsEgg = ingredientsLower.contains("egg", true)
            isSweet && !containsEgg
        }
    }
}