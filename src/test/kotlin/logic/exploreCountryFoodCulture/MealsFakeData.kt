package logic.exploreCountryFoodCulture

import mealHelperTest.createMeal

object MealsFakeData {
    val mealsWithEgyptInTags = List(100) { createMeal(id = it, tags = listOf("egypt")) }
    val mealsWithEgyptInDescription = List(100) { createMeal(id = it, description = "an egyptian meal") }
    val mealsWithEgyptInName = List(100) { createMeal(id = it, name = "egypt meal $it") }
    val egyptianMealList = mealsWithEgyptInTags + mealsWithEgyptInDescription + mealsWithEgyptInName
}