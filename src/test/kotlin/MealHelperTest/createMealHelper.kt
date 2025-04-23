package MealHelperTest

import model.Meal
import model.Nutrition
import java.time.LocalDate

fun createMeal(
    id: Int = 0,
    name: String = "Test Meal",
    minutes: Int? = null,
    dateSubmitted: LocalDate? = null,
    tags: List<String> = listOf(""),
    nutrition: Nutrition = createNutrition(null, null, null, null, null, null, null),
    steps: List<String> = listOf(),
    description: String = "",
    ingredients: List<String> = listOf()
): Meal = Meal(
    id = id,
    name = name,
    minutes = minutes,
    dateSubmitted = dateSubmitted,
    tags = tags,
    nutrition = nutrition,
    steps = steps,
    description = description,
    ingredients = ingredients
)
