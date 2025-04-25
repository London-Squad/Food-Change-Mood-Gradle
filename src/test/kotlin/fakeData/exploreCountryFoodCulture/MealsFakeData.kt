package fakeData.exploreCountryFoodCulture

import mealHelperTest.createMeal

object MealsFakeData {
    val mealsWithEgyptInTags = List(100) { createMeal(id = it, tags = listOf("egypt")) }
    val mealsWithItalianInDescription = List(100) { createMeal(id = 100 + it, description = "an Italy meal") }
    val mealsWithIraqInName = List(100) { createMeal(id = 200 + it, name = "iraq meal $it") }
    val mealList = mealsWithEgyptInTags + mealsWithItalianInDescription + mealsWithIraqInName
}