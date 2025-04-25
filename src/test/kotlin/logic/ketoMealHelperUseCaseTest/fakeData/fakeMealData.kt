package logic.ketoMealHelperUseCaseTest.fakeData

import mealHelperTest.createMeal
import mealHelperTest.createNutrition

object FackDataMeals {
    val validNutrition = createNutrition(
        calories = 500f, carbohydrates = 5f, sugar = 2f, totalFat = 40f, protein = 32f, saturatedFat = 9.2f, sodium = 1f
    )
    val validMealWithValidNutrition = createMeal(
        id = 9, nutrition = validNutrition, ingredients = listOf("chicken", "avocado")
    )

    val disallowedIngredients = listOf(
        "bread",
        "pasta",
        "rice",
        "potato",
        "sugar",
        "high fructose corn syrup",
        "corn",
        "wheat",
        "flour",
        "legume",
        "bean",
        "soda",
        "milk",
        "skim milk",
        "honey",
        "agave",
        "cereal",
        "oat",
        "quinoa",
        "yogurt",
        "juice"
    )
    val mealWithCaloriesNull =
        createMeal(nutrition = createNutrition(calories = null, totalFat = 2.0f, sugar = 10.0f, protein = 1.0f))
    val mealWithCarbohydratesNull = createMeal(
        nutrition = createNutrition(
            carbohydrates = null,
            calories = 5.0f,
            totalFat = 2.0f,
            sugar = 10.0f,
            protein = 1.0f
        )
    )
    val mealWithCarbohydratesThatExceedMaxAllowed = createMeal(
        nutrition = createNutrition(
            calories = 500.0f,
            carbohydrates = 13.0f,
            sugar = 5.0f,
            totalFat = 45.0f,
            protein = 35.0f,
            saturatedFat = 10.35f
        )
    )

    val mealWithNullSugar = createMeal(
        nutrition = createNutrition(
            sugar = null, calories = 1000f, carbohydrates = 10f, totalFat = 70f, protein = 30f, saturatedFat = 20f
        )
    )

    val mealWithNullTotalFat = createMeal(
        nutrition = createNutrition(
            totalFat = null, calories = 1000f, carbohydrates = 10f, sugar = 5f, protein = 30f, saturatedFat = 20f
        )
    )

    val mealWithNullProtein = createMeal(
        nutrition = createNutrition(
            protein = null, calories = 1000f, carbohydrates = 10f, sugar = 5f, totalFat = 70f, saturatedFat = 20f
        )
    )

    val mealWithNullSaturatedFat = createMeal(
        nutrition = createNutrition(
            saturatedFat = null, calories = 1000f, carbohydrates = 10f, sugar = 5f, totalFat = 70f, protein = 30f
        )
    )

    val mealWithHighSugar = createMeal(
        nutrition = createNutrition(
            calories = 100f, sugar = 6f, carbohydrates = 5f, totalFat = 70f, protein = 30f, saturatedFat = 20f
        )
    )

    val mealWithLowProtein = createMeal(
        nutrition = createNutrition(
            calories = 100f, protein = 6f, carbohydrates = 5f, sugar = 5f, totalFat = 70f, saturatedFat = 20f
        )
    )

    val mealWithInvalidSaturatedFat = createMeal(
        nutrition = createNutrition(
            totalFat = 100f, saturatedFat = 19f, calories = 1000f, carbohydrates = 10f, sugar = 5f, protein = 30f
        )
    )

    val mealWithLowTotalFatPercentage = createMeal(
        id = 10,
        nutrition = createNutrition(
            calories = 500f,
            carbohydrates = 5.0f,
            sugar = 4.0f,
            totalFat = 2.0f,  // دهون قليلة جدًا
            protein = 25.0f,
            saturatedFat = 1.0f
        ),
        ingredients = listOf("chicken", "spinach", "olive oil")
    )


}

