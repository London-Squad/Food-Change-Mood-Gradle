package logic.getIraqiMeals

import MealHelperTest.createMeal


object FakeIraqiMeals {
    val iraqiMealWithTagAndDesc = createMeal(
        name = "Dolma",
        tags = listOf("iraqi"),
        description = "The best iraqi meal"
    )

    val iraqiMealWithTag = createMeal(
        name = "Another Meal",
        tags = listOf("iraqi")
    )

    val iraqiMealWithDesc = createMeal(
        name = "Another Meal",
        description = "Iraqi meal"
    )

    private val nonIraqiMeal = createMeal(
        id = 4,
        name = "Some Meal",
        tags = listOf("some")
    )

    val mealsWithoutIraqi = listOf(
        nonIraqiMeal
    )

    val allMeals = listOf(
        iraqiMealWithTagAndDesc,
        iraqiMealWithTag,
        iraqiMealWithDesc,
        nonIraqiMeal
    )

}