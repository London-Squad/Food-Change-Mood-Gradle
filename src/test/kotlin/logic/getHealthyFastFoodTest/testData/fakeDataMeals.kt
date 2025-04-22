package logic.getHealthyFastFoodTest.testData

import MealHelperTest.createNutrition
import model.Meal
import model.Nutrition
import test.logic.getHealthyFastFoodTest.createMeal
import java.time.LocalDate

object FakeDataMeals {

    val healthyMeal = Meal(
        id = 27752,
        name = "zucchini with pine nuts and orange",
        minutes = 10,
        dateSubmitted = LocalDate.parse("2020-01-01"),
        tags = listOf("healthy", "fast"),
        nutrition = Nutrition(73.7f, 8.0f, 13.0f, 0.0f, 2.0f, 3.0f, 1.0f),
        steps = listOf(),
        description = "",
        ingredients = listOf()
    )

    val longPrepMeal = Meal(
        id = 357551,
        name = "any sauce",
        minutes = 20,
        dateSubmitted = LocalDate.parse("2020-01-01"),
        tags = listOf(),
        nutrition = Nutrition(239.9f, 30.0f, 19.0f, 22.0f, 1.0f, 14.0f, 5.0f),
        steps = listOf(),
        description = "",
        ingredients = listOf()
    )

    val mealwithHighNutrition = Meal(
        id = 357552,
        name = "Souce meal",
        minutes = 20,
        dateSubmitted = LocalDate.parse("2020-01-01"),
        tags = listOf(),
        nutrition = Nutrition(500.9f, 50.0f, 50.0f, 30.0f, 1.0f, 14.0f, 5.0f),
        steps = listOf(),
        description = "",
        ingredients = listOf()
    )

    val mealWithNullNutritionValue = createMeal(
        id = 4,
        name = "Incomplete Nutrition",
        minutes = 10,
        nutrition = createNutrition(
            totalFat = null,
            saturatedFat = 1f,
            carbohydrates = 10f
        )
    )

    val highFatMeal = createMeal(
        id = 2,
        name = "Too Fatty",
        minutes = 10,
        nutrition = createNutrition(
            totalFat = 20f,
            saturatedFat = 5f,
            carbohydrates = 10f
        )
    )

    val highCarbMeal = createMeal(
        id = 3,
        name = "Too Carby",
        minutes = 10,
        nutrition = createNutrition(
            totalFat = 5f,
            saturatedFat = 2f,
            carbohydrates = 40f
        )
    )

    val incompleteMeal = Meal(
        id = 12345,
        name = "incomplete meal",
        minutes = 10,
        dateSubmitted = LocalDate.parse("2020-01-01"),
        tags = listOf(),
        nutrition = Nutrition(null, null, null, null, null, null, null),
        steps = listOf(),
        description = "",
        ingredients = listOf()
    )

    val mealWithNullSaturatedFat = createMeal(
        id = 6,
        name = "Null saturatedFat",
        minutes = 10,
        nutrition = createNutrition(
            totalFat = 5f,
            saturatedFat = null,
            carbohydrates = 10f
        )
    )

    val mealWithNullableFat = createMeal(
        id = 7,
        name = "Null Fat",
        minutes = 10,
        nutrition = createNutrition(
            totalFat = null,
            saturatedFat = 1f,
            carbohydrates = 2f
        )
    )

    val mealWithNullableCarb = createMeal(
        id = 8,
        name = "Null carbs",
        minutes = 10,
        nutrition = createNutrition(
            totalFat = 5f,
            saturatedFat = 1f,
            carbohydrates = null
        )
    )
    val boundaryTimeMeal = createMeal(
        id = 9,
        name = "Boundary Prep Time",
        minutes = 15,
        nutrition = createNutrition(
            totalFat = 5f,
            saturatedFat = 1f,
            carbohydrates = 10f
        )
    )

    val validMeal = createMeal(
        id = 10,
        name = "Valid Meal",
        minutes = 10,
        nutrition = createNutrition(
            totalFat = 4f,
            saturatedFat = 1f,
            carbohydrates = 8f
        )
    )

    val avgMeal1 = createMeal(
        id = 11,
        name = "Average Meal 1",
        minutes = 10,
        nutrition = createNutrition(
            totalFat = 5f,
            saturatedFat = 1f,
            carbohydrates = 10f
        )
    )

    val avgMeal2 = createMeal(
        id = 12,
        name = "Average Meal 2",
        minutes = 12,
        nutrition = createNutrition(
            totalFat = 5f,
            saturatedFat = 1f,
            carbohydrates = 10f
        )
    )

    val zeroNutritionMeal = createMeal(
        id = 13,
        name = "Zero Nutrition",
        minutes = 8,
        nutrition = createNutrition(
            totalFat = 0f,
            saturatedFat = 0f,
            carbohydrates = 0f
        )
    )

    val nanMeals = listOf(
        createMeal(
            id = 14,
            name = "NaN Meal 1",
            minutes = 10,
            nutrition = createNutrition(
                totalFat = Float.NaN,
                saturatedFat = 1f,
                carbohydrates = 10f
            )
        ),
        createMeal(
            id = 15,
            name = "NaN Meal 2",
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 5f,
                saturatedFat = Float.NaN,
                carbohydrates = 10f
            )
        )
    )

    val invalidInitialMeals = listOf(
        longPrepMeal,
        mealWithNullSaturatedFat,
        mealWithNullNutritionValue,
        mealWithNullableFat,
        mealWithNullableCarb
    )


    val allSamplesMealsCase = listOf(
        healthyMeal,
        longPrepMeal,
        incompleteMeal,
        mealWithNullableCarb,
        mealWithNullableFat,
        mealwithHighNutrition,
        highCarbMeal,
        highFatMeal
    )

    val invalidHealthyFood = listOf(
        longPrepMeal,
        mealwithHighNutrition,
        incompleteMeal
    )
}
