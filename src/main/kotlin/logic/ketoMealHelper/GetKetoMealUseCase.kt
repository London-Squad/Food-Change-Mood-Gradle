package logic.ketoMealHelper

import logic.MealSuggester
import logic.MealsDataSource
import model.Meal
import model.Nutrition

class GetKetoMealUseCase(
    mealsDataSource: MealsDataSource,
) : MealSuggester(mealsDataSource) {

    fun initSuggestions() {
        initSuggestedList()
    }

    fun suggestKetoMeal(): Meal? {
        return suggestMeal()
    }

    override fun isValidSuggestion(meal: Meal): Boolean {
        return isKetoFriendly(meal)
    }

    private fun isKetoFriendly(meal: Meal): Boolean {
        return (isPassesKetoNutritionCheck(meal.nutrition) && containsAllowedIngredients(meal))
    }

    private fun isPassesKetoNutritionCheck(nutrition: Nutrition): Boolean {
        nutrition.apply {
            return (calories != null) &&
                    (carbohydrates != null && (carbohydrates * CARB_CALORIES_BER_GRAM / calories * 100) <= MAX_CARBOHYDRATES_GRAMS) &&
                    (sugar != null && (sugar / calories * 100) <= MAX_SUGAR_GRAMS) &&
                    (totalFat != null && (totalFat * FAT_CALORIES_BER_GRAM / calories * 100) >= MIN_TOTAL_FAT_GRAMS) &&
                    (protein != null && (protein * PROTEIN_CALORIES_BER_GRAM / calories * 100) >= MIN_PROTEIN_GRAMS) &&
                    (saturatedFat != null && (saturatedFat / totalFat * 100) in MAX_SATURATED_FAT_GRAMS)
        }
    }


    private fun containsAllowedIngredients(meal: Meal): Boolean {
        val hasNotAllowedIngredient = meal.ingredients.any { ingredient ->
            val lowerIngredient = ingredient.lowercase()
            notAllowedIngredients.any {
                lowerIngredient.contains(it.lowercase())
            }
        }
        return !hasNotAllowedIngredient
    }

    private companion object {
        const val FAT_CALORIES_BER_GRAM = 9f
        const val PROTEIN_CALORIES_BER_GRAM = 4f
        const val CARB_CALORIES_BER_GRAM = 4f

        const val MAX_CARBOHYDRATES_GRAMS = 10f
        const val MAX_SUGAR_GRAMS = 5f
        const val MIN_TOTAL_FAT_GRAMS = 60f
        const val MIN_PROTEIN_GRAMS = 25f
        val MAX_SATURATED_FAT_GRAMS = 20f..25f

        val notAllowedIngredients = listOf(
            "bread", "pasta", "rice", "potato", "sugar", "high fructose corn syrup",
            "corn", "wheat", "flour", "legume", "bean", "soda", "milk", "skim milk",
            "honey", "agave", "cereal", "oat", "quinoa", "yogurt", "juice",
        )
    }
}
