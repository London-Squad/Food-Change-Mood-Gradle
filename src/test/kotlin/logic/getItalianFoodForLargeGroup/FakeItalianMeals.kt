package logic.getItalianFoodForLargeGroup

import mealHelperTest.createMeal


object FakeItalianMeals {
    val mealWithItalianTag = createMeal(
        name = "Pasta",
        tags = listOf("italian", "for-large-groups")
    )

    val mealWithItalianDesc = createMeal(
        name = "Pizza",
        tags = listOf("for-large-groups"),
        description = "A classic meal from Italy"
    )

    val mealWithItalianName = createMeal(
        name = "Italy Special",
        tags = listOf("for-large-groups")
    )

    private val nonItalianNonLargeGroupMeal = createMeal(
        name = "Burger",
        tags = listOf("fast-food")
    )

    private val italyMealWithoutLargeGroupTag = createMeal(
        name = "Italy Special",
        tags = listOf("italian")
    )

    val nonItalianMeals = listOf(
        nonItalianNonLargeGroupMeal
    )

    val italianMealsWithoutLargeGroupTag = listOf(
        italyMealWithoutLargeGroupTag
    )

    val allMeals = listOf(
        mealWithItalianTag,
        mealWithItalianDesc,
        mealWithItalianName,
        nonItalianNonLargeGroupMeal,
        italyMealWithoutLargeGroupTag
    )
}