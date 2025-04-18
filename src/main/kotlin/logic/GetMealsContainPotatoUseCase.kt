package logic

import model.Meal

class GetMealsContainPotatoUseCase(
    private val mealsDataSource: MealsDataSource
) {

    fun getRandomMeals(): List<Meal> {
        return mealsDataSource.getAllMeals()
            .getRandomMeals(::isMealContainPotato, NUMBER_OF_MEALS_TO_PRESENT)
    }

    private fun List<Meal>.getRandomMeals(condition: (Meal) -> Boolean, count: Int): List<Meal> {
        return this.filter(condition).run {
            if (size < count) this
            else shuffled().take(count)
        }
    }

    private fun isMealContainPotato(meal: Meal): Boolean =
        meal.tags.contains(POTATOES_TAG) || meal.ingredients.contains(POTATO_KEYWORD)

    private companion object {
        const val POTATOES_TAG = "potatoes"
        const val POTATO_KEYWORD = "potato"
        const val NUMBER_OF_MEALS_TO_PRESENT = 10
    }
}