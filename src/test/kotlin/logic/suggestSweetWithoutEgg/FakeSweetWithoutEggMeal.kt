package logic.suggestSweetWithoutEgg

import mealHelperTest.createMeal

object FakeSweetWithoutEggMeal {
    val sweetEggFreeMeal = createMeal(
        name = "Sweet Egg-Free Cake",
        tags = listOf("sweet", "egg-free"),
        ingredients = listOf("flour", "sugar")
    )

    val nonSweetMeal = createMeal(
        name = "Bitter Dish",
        tags = listOf("bitter"),
        ingredients = listOf("egg", "salt")
    )


    val sweetEggFreeMeal2 = createMeal(
        name = "Sweet Egg-Free Pie",
        tags = listOf("sweet", "egg-free"),
        ingredients = listOf("flour", "butter")
    )

    val sweetEggFreeMeal3 = createMeal(
        name = "Egg-Free Dish",
        tags = listOf("sweet"),
        ingredients = listOf("flour", "sugar")
    )


    private val mealWithEgg = createMeal(
        name = "Dish with Egg",
        tags = listOf("sweet"),
        ingredients = listOf("flour", "egg")
    )

    private val mealWithEggs = createMeal(
        name = "Dish with Eggs",
        tags = listOf("sweet"),
        ingredients = listOf("flour", "eggs")
    )

    val mealsWithEggs = listOf(
        mealWithEgg,
        mealWithEggs
    )

    val sweetMealsWithoutEggs = listOf(
        sweetEggFreeMeal,
        sweetEggFreeMeal2,
        sweetEggFreeMeal3
    )

    val allMeals = listOf(
        mealWithEgg,
        mealWithEggs,
        sweetEggFreeMeal,
        sweetEggFreeMeal2,
        sweetEggFreeMeal3
    )
}