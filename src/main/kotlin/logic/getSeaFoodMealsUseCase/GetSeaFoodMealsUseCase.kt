package logic.getSeaFoodMealsUseCase

import logic.MealsDataSource
import model.Meal

class GetSeaFoodMealsUseCase(
    private val mealDataSource : MealsDataSource
) {
    fun getSeaFoodMealsSortedByProtein(): List<Meal> =
        mealDataSource
            .getAllMeals()
            .filter { meal-> meal.nutrition.protein != null  && isSeaFood(meal)}
            .sortedByDescending { meal-> meal.nutrition.protein }


    private fun isSeaFood(meal: Meal): Boolean =
        SEA_FOOD_KEYWORDS.any{
            meal.tags.any {tag -> it in tag.lowercase()}
            || meal.description.lowercase().contains(it)
            || meal.name.lowercase().contains(it)
        }

    private companion object {
        val SEA_FOOD_KEYWORDS = listOf(
            "seafood",
            "sea food",
            "sea-food",
        )
    }
}