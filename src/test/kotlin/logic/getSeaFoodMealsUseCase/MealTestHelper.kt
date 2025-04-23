package logic.testutils

import model.Meal
import model.Nutrition

object MealTestUtils {
    fun createMeal(
        name: String = "",
        description: String = "",
        tags: List<String> = emptyList(),
        protein: Float? = null,
        calories: Float? = null,
        totalFat: Float? = null,
        sugar: Float? = null,
        sodium: Float? = null,
        saturatedFat: Float? = null,
        carbohydrates: Float? = null
    ): Meal = Meal(
        id = 0,
        name = name,
        minutes = null,
        dateSubmitted = null,
        tags = tags,
        nutrition = Nutrition(
            calories = calories,
            totalFat = totalFat,
            sugar = sugar,
            sodium = sodium,
            protein = protein,
            saturatedFat = saturatedFat,
            carbohydrates = carbohydrates
        ),
        steps = emptyList(),
        description = description,
        ingredients = emptyList()
    )
}