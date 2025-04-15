package model

import java.time.LocalDate

data class Meal(
    val id: UInt,
    val name: String?,
    val minutes: Int?,
    val contributorId: UInt?,
    val submitted: LocalDate?,
    val tags: List<String?>,
    val nutrition: Nutrition?,
    val numberOfSteps: UInt?,
    val steps: List<String?>,
    val description: String,
    val ingredients: List<String?>,
    val numberOfIngredients: UInt?
)