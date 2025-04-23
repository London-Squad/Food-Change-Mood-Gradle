package logic.search

import model.Meal
import model.Nutrition
import java.time.LocalDate

object TestMealsProvider {

    val mealForNameSearch1 = Meal(
        id = 1,
        name = "Chicken Curry",
        minutes = 45,
        dateSubmitted = LocalDate.of(2023, 4, 16),
        tags = emptyList(),
        nutrition = Nutrition(
            calories = 600f,
            totalFat = 20f,
            sugar = 5f,
            sodium = 800f,
            protein = 30f,
            saturatedFat = 8f,
            carbohydrates = 50f
        ),
        steps = emptyList(),
        description = "Spicy",
        ingredients = emptyList()
    )

    val mealForNameSearch2 = Meal(
        id = 2,
        name = "Beef Stew",
        minutes = 60,
        dateSubmitted = LocalDate.of(2023, 4, 17),
        tags = emptyList(),
        nutrition = Nutrition(
            calories = 700f,
            totalFat = 25f,
            sugar = 3f,
            sodium = 900f,
            protein = 40f,
            saturatedFat = 10f,
            carbohydrates = 30f
        ),
        steps = emptyList(),
        description = "Hearty",
        ingredients = emptyList()
    )

    val mealsForNameSearch: List<Meal> = listOf(mealForNameSearch1, mealForNameSearch2)

    val mealForDateSearchA = Meal(
        id = 101,
        name = "Pork Ribs",
        minutes = 50,
        dateSubmitted = LocalDate.of(2023, 5, 1),
        tags = emptyList(),
        nutrition = Nutrition(
            calories = 800f,
            totalFat = 30f,
            sugar = 2f,
            sodium = 1000f,
            protein = 35f,
            saturatedFat = 12f,
            carbohydrates = 20f
        ),
        steps = emptyList(),
        description = "Smoky",
        ingredients = emptyList()
    )

    val mealForDateSearchB = Meal(
        id = 102,
        name = "Lamb Chops",
        minutes = 40,
        dateSubmitted = LocalDate.of(2023, 5, 1),
        tags = emptyList(),
        nutrition = Nutrition(
            calories = 650f,
            totalFat = 28f,
            sugar = 1f,
            sodium = 850f,
            protein = 38f,
            saturatedFat = 10f,
            carbohydrates = 15f
        ),
        steps = emptyList(),
        description = "Savory",
        ingredients = emptyList()
    )

    val mealForDateSearchC = Meal(
        id = 103,
        name = "Fish Tacos",
        minutes = 30,
        dateSubmitted = LocalDate.of(2023, 6, 1),
        tags = emptyList(),
        nutrition = Nutrition(
            calories = 500f,
            totalFat = 15f,
            sugar = 4f,
            sodium = 700f,
            protein = 25f,
            saturatedFat = 5f,
            carbohydrates = 40f
        ),
        steps = emptyList(),
        description = "Fresh",
        ingredients = emptyList()
    )

    val mealsForDateSearch: List<Meal> = listOf(mealForDateSearchA, mealForDateSearchB, mealForDateSearchC)
}