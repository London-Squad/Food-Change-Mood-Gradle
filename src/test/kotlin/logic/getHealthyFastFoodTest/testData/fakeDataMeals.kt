package logic.getHealthyFastFoodTest.testData

import MealHelperTest.createNutrition
import model.Meal
import test.logic.getHealthyFastFoodTest.createMeal

object FakeDataMeals {

    val healthyMeal = createMeal(
        id = 1, minutes = 10, nutrition = createNutrition(
            totalFat = 5f, saturatedFat = 2f, carbohydrates = 10f
        )
    )

    val highFatMeal = createMeal(
        id = 2, name = "Too Fatty", minutes = 10, nutrition = createNutrition(
            totalFat = 20f, saturatedFat = 5f, carbohydrates = 30f
        )
    )

    val longPrepMeal = createMeal(
        minutes = 20, nutrition = createNutrition(
            totalFat = 5f, saturatedFat = 2f, carbohydrates = 10f
        )
    )

    val mealWithNullNutritionValue = createMeal(
        id = 5
    )

    val mealWithNullSaturatedFat = createMeal(
        nutrition = createNutrition(saturatedFat = null)
    )

    val mealWithNullableCarb = createMeal(
        nutrition = createNutrition(carbohydrates = null)
    )

    val malformedMeals = listOf(
        createMeal(minutes = null),
        createMeal(nutrition = createNutrition(totalFat = null)),
        createMeal(nutrition = createNutrition(saturatedFat = null)),
        createMeal(nutrition = createNutrition(carbohydrates = null)),
        createMeal(minutes = 20)
    )

    val invalidNaNMeals = listOf(
        createMeal(
            minutes = 10, nutrition = createNutrition(
                totalFat = 5f, saturatedFat = Float.NaN, carbohydrates = 10f
            )
        ), createMeal(
            minutes = 10, nutrition = createNutrition(
                totalFat = 6f, saturatedFat = Float.NaN, carbohydrates = 12f
            )
        )
    )


    val invalidHealthyFood = listOf(
        createMeal(minutes = 16), createMeal(id = 4), createMeal(nutrition = createNutrition(totalFat = null))
    )

    val allSamplesMealsCase = listOf(healthyMeal, longPrepMeal, highFatMeal)

    val mealWithFatJustAboveAverage = createMeal(
        id = 6, minutes = 10, nutrition = createNutrition(
            totalFat = 15.1f, saturatedFat = 2f, carbohydrates = 10f
        )
    )

    val mealWithCarbsJustAboveAverage = createMeal(
        id = 7, minutes = 10, nutrition = createNutrition(
            totalFat = 5f, saturatedFat = 2f, carbohydrates = 25.1f
        )
    )

    val mealWithNullFatButValidCarbs = createMeal(
        id = 8, minutes = 10, nutrition = createNutrition(
            totalFat = null, saturatedFat = null, carbohydrates = 10f
        )
    )

    val mealWithNullCarbsButValidFat = createMeal(
        id = 9, minutes = 10, nutrition = createNutrition(
            totalFat = 5f, saturatedFat = 2f, carbohydrates = null
        )
    )

    val slightlyAbove = createMeal(
        minutes = 10, nutrition = createNutrition(
            totalFat = 10.0001f, carbohydrates = 15f
        )
    )
    @JvmStatic
    fun emptyOrInvalidMealsProvider(): List<List<Meal>> = listOf(
        emptyList(),
        FakeDataMeals.invalidHealthyFood,
        listOf(FakeDataMeals.mealWithNullNutritionValue),
        listOf(FakeDataMeals.longPrepMeal),
        listOf(FakeDataMeals.mealWithNullSaturatedFat),
        listOf(FakeDataMeals.mealWithNullableCarb),
        FakeDataMeals.malformedMeals,
        FakeDataMeals.invalidNaNMeals
    )
}