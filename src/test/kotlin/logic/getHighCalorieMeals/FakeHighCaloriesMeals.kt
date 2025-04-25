package logic.getHighCalorieMeals

import mealHelperTest.createMeal
import mealHelperTest.createNutrition

object FakeHighCaloriesMeals {

    val mealWithNullCalories = createMeal(
        name = "Null Calories Meal"
    )

    val mealWithCalories699 = createMeal(
        name = "699 Calories Meal",
        nutrition = createNutrition(calories = 699f)
    )

    val mealWithHighCalories700 = createMeal(
        name = "700 Calories Meal",
        nutrition = createNutrition(calories = 700f)
    )

    val mealWithHighCalories701 = createMeal(
        name = "701 Calories Meal",
        nutrition = createNutrition(calories = 701f)
    )

    val mealWithHighCalories840 = createMeal(
        name = "840 Calories Meal",
        nutrition = createNutrition(calories = 840f)
    )

    val mealWithHighCalories1200 = createMeal(
        name = "1200 Calories Meal",
        nutrition = createNutrition(calories = 1200f)
    )

    val mealWithNegativeCalories = createMeal(
        name = "-1 Calories Meal",
        nutrition = createNutrition(calories = -1f)
    )


    val allInvalidMeals = listOf(
        mealWithNullCalories,
        mealWithNegativeCalories
    )
    val allLowCaloriesMeals = listOf(
        mealWithCalories699,
        mealWithHighCalories700
    )

    val allHighCaloriesMeals = listOf(
        mealWithHighCalories701,
        mealWithHighCalories840,
        mealWithHighCalories1200
    )

    val allMeals = allInvalidMeals + allLowCaloriesMeals + allHighCaloriesMeals
}