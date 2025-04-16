package logic

import model.Meal

fun getSweetsWithoutEggs(meals: List<Meal>): List<Meal> {
    return meals.filter { meal ->
        val nameLower = meal.name?.lowercase() ?: return@filter false
        val ingredientsLower = meal.ingredients.joinToString(",") { it?.lowercase() ?: "" }
        val isSweet = listOf("cake", "cookie", "dessert", "sweet", "brownie", "pie", "pudding").any { it in nameLower }
        val containsEgg = ingredientsLower.contains("egg")
        isSweet && !containsEgg
    }.distinctBy { it.name }
}
class SweetSuggester(mealsDataSource: MealsDataSource) {
    private val eggFreeSweets = getSweetsWithoutEggs(mealsDataSource.getAllMeals()).shuffled().toMutableList()
    private val suggested = mutableSetOf<String>()

    fun suggestSweet(): Meal? {
        val next = eggFreeSweets.firstOrNull { it.name !in suggested }
        next?.let { it.name?.let { name -> suggested.add(name) } }
        return next
    }

    fun likeSweet(sweet: Meal) {
        println("You liked: ${sweet.name}")
        println("Full details:")
        println("Description: ${sweet.description}")
        println("Ingredients: ${sweet.ingredients.filterNotNull().joinToString()}")
        println("Steps: ${sweet.steps.filterNotNull().joinToString()}")
        println("Time: ${sweet.minutes ?: "N/A"} mins")
    }

    fun dislikeSweet(): Meal? {
        return suggestSweet()
    }
}