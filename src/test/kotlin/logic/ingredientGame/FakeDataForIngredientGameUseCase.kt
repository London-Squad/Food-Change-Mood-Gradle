package logic.ingredientGame

import model.Meal
import model.Nutrition

class FakeDataForIngredientGameUseCase {
    private fun createMeal(name: String, ingredients: List<String>): Meal = Meal(
        0,
        name,
        null,
        null,
        ingredients,
        Nutrition(null, null, null, null, null, null, null, ),
        listOf(),
        "",
        listOf()
    )

    private val meal1 = createMeal("meal 1", listOf("ingredient 1.1", "ingredient 1.2", "ingredient 1.3"))
    private val meal2 = createMeal("meal 2", listOf("ingredient 2.1", "ingredient 2.2", "ingredient 2.3"))
    private val meal3 = createMeal("meal 3", listOf("ingredient 3.1", "ingredient 3.2", "ingredient 3.3"))

    val mealsList = listOf(meal1, meal2, meal3)
    val mealsListWithOneMeal = listOf(meal1)
    val mealsListWithTwoMeal = listOf(meal1, meal2)
    val emptyMealsList: List<Meal> = listOf()
}