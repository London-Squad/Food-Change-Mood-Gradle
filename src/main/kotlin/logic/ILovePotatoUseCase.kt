package logic

import model.Meal

class ILovePotatoUseCase(
    private val mealsDataSource: MealsDataSource
) {

    fun getRandomMeals(): List<Meal> {
        return mealsDataSource.getAllMeals()
            .getRandomMeals(::isMealContainPotato, 10)
    }

    private fun List<Meal>.getRandomMeals(condition: (Meal) -> Boolean, count: Int): List<Meal> {
        return this.filter(condition).run {
            if (size < count) this
            else shuffled().slice(0..<count)
        }
    }

    private fun isMealContainPotato(meal: Meal): Boolean =
        meal.tags.contains(POTATOES_TAG) || meal.ingredients.contains(POTATO_KEYWORD)

    private companion object {
        const val POTATOES_TAG = "potatoes"
        const val POTATO_KEYWORD = "potato"
    }
}