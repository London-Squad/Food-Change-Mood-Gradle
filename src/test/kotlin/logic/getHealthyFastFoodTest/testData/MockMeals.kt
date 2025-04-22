package logic.getHealthyFastFoodTest.testData

import model.Meal
import model.Nutrition
import java.time.LocalDate

object MockMeals {

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

    val allMeals = listOf(healthyMeal, longPrepMeal, incompleteMeal)
    val invalidHealthyFood = listOf(longPrepMeal, incompleteMeal)
}
