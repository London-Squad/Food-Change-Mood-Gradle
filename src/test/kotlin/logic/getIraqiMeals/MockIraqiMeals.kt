package logic.getIraqiMeals

import test.logic.getHealthyFastFoodTest.createMeal

object MockIraqiMeals {
    val iraqiMealsWithTagAndDesc = createMeal(
        id = 1,
        name = "Dolma",
        tags = listOf("iraqi"),
        description = "The best iraqi meal"
    )

    val iraqiMealWithTag = createMeal(
        id = 2,
        name = "Another Meal",
        tags = listOf("iraqi")
    )

    val iraqiMealWithDesc = createMeal(
        id = 3,
        name = "Another Meal",
        description = "Iraqi meal"
    )

    val someMeal = createMeal(
        id = 4,
        name = "Some Meal",
        tags = listOf("some")
    )

    val mealsWithoutIraqi = listOf(
        someMeal
    )

    val allMeals = listOf(
        iraqiMealsWithTagAndDesc,
        iraqiMealWithTag,
        iraqiMealWithDesc,
        someMeal
    )

}