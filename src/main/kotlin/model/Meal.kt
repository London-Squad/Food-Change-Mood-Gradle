package model

import java.time.LocalDate

data class Meal(
    val id: Int,
    val name: String,
    val minutes: Int?,
    val dateSubmitted: LocalDate?,
    val tags: List<String>,
    val nutrition: Nutrition,
    val steps: List<String>,
    val description: String,
    val ingredients: List<String>,
)