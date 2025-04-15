package model

import java.time.LocalDate

data class Meal(
    val id: Int,
    val name: String,
    val minutes: Int,
    val contributorId: Int,
    val submitted: LocalDate,
    val tags: List<String>,
    val nutrition: Nutrition,
    val numberOfSteps: Int,
    val steps: List<String>,
    val description: String,
    val ingredients: List<String>,
    val numberOfIngredients: Int
)