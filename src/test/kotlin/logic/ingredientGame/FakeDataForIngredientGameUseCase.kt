package logic.ingredientGame

import createMeal
import model.Meal

object FakeDataForIngredientGameUseCase {
    val meal1 = createMeal(name = "meal 1", ingredients = listOf("ingredient 1.1", "ingredient 1.2", "ingredient 1.3"))
    val meal2 = createMeal(name = "meal 2", ingredients = listOf("ingredient 2.1", "ingredient 2.2", "ingredient 2.3"))
    val meal3 = createMeal(name = "meal 3", ingredients = listOf("ingredient 3.1", "ingredient 3.2", "ingredient 3.3"))

    val mealsList = listOf(meal1, meal2, meal3)
    val mealsListWithOneMeal = listOf(meal1)
    val emptyMealsList: List<Meal> = listOf()

}