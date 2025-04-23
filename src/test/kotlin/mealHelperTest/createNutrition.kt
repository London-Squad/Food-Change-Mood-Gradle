package mealHelperTest


import model.Nutrition

fun createNutrition(
    calories: Float? = null,
    totalFat: Float? = null,
    sugar: Float? = null,
    sodium: Float? = null,
    protein: Float? = null,
    saturatedFat: Float? = null,
    carbohydrates: Float? = null
): Nutrition {
    return Nutrition(
        calories = calories,
        totalFat = totalFat,
        sugar = sugar,
        sodium = sodium,
        protein = protein,
        saturatedFat = saturatedFat,
        carbohydrates = carbohydrates
    )
}