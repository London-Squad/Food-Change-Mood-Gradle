package logic.suggestSweetWithoutEgg

import logic.MealSuggester
import logic.MealsDataSource
import model.Meal

class GetSweetWithoutEggUseCase(mealsDataSource: MealsDataSource) : MealSuggester(mealsDataSource) {

    override fun isValidSuggestion(meal: Meal): Boolean {
        return isSweet(meal) && isMealWithoutEgg(meal)
    }

    private fun isMealWithoutEgg(meal: Meal): Boolean =
        meal.run {
            tags.any { it.contains("egg-free", ignoreCase = true) }
                    || !(
                    ingredients.any { it.contains("egg", ignoreCase = true) }
                            || ingredients.any { it.contains("eggs", ignoreCase = true) }
                    )
        }

    private fun isSweet(meal: Meal): Boolean = meal.tags.any { it.contains("sweet", ignoreCase = true) }

}

