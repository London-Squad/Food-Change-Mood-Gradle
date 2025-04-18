package logic

import model.Meal

class SuggestSweetWithoutEggUseCase(mealsDataSource: MealsDataSource) {
    private val eggFreeSweets = getSweetsWithoutEggs(mealsDataSource.getAllMeals())
    private val suggested = mutableSetOf<Int>()

    fun suggestSweet(): Meal? {
        try{
            val next = eggFreeSweets.shuffled().first { it.id !in suggested }
            next.let { it.id.let { id -> suggested.add(id) } }
            return next
        }catch (e: Exception) {
            return null
        }
    }

    fun clearSuggestedList(){
        suggested.clear()
    }

    private fun getSweetsWithoutEggs(meals: List<Meal>): List<Meal> {
        return meals.filter { meal ->
            val nameLower = meal.name.lowercase()
            val ingredientsLower = meal.ingredients.joinToString(",")
            val isSweet = listOf("cake", "cookie", "dessert", "sweet", "brownie", "pie", "pudding").any { it in nameLower }
            val containsEgg = ingredientsLower.contains("egg", true)
            isSweet && !containsEgg
        }
    }
}