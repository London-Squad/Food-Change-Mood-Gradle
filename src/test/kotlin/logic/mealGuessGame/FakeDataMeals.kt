package logic.mealGuessGame

import MealHelperTest.createMeal

object FakeDataMeals {
    val mealWithNullTime = createMeal(
        name = "Null Time Meal",
    )

    val mealWithNegativeTime = createMeal(
        name = "Negative Time Meal",
        minutes = -1
    )

    val mealWithValidTime1 = createMeal(
        name = "Valid Meal 1",
        minutes = 40
    )

    val mealWithValidTime2 = createMeal(
        name = "Valid Meal 2",
        minutes = 30
    )
    val mealWithValidTime3 = createMeal(
        name = "Valid Meal 3",
        minutes = 15
    )
    val mealWithValidTime4 = createMeal(
        name = "Valid Meal 4",
        minutes = 10
    )
    val mealWithValidTime5 = createMeal(
        name = "Valid Meal 5",
        minutes = 5
    )

    val allMeals = listOf(
        mealWithValidTime1,
        mealWithValidTime2,
        mealWithValidTime3,
        mealWithValidTime4,
        mealWithValidTime5,
        mealWithNegativeTime,
        mealWithNullTime
    )

    val allInvalidTimeMeals = listOf(
        mealWithNegativeTime,
        mealWithNullTime
    )

    val allValidTimeMeals = listOf(
        mealWithValidTime1,
        mealWithValidTime2,
        mealWithValidTime3,
        mealWithValidTime4,
        mealWithValidTime5
    )
}