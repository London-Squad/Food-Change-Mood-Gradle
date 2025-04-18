package logic

import model.Meal
import model.Nutrition

class KetoFriendlyValidator {
    private companion object {
        const val MAX_CARBOHYDRATES_GRAMS = 10f
        const val MAX_SUGAR_GRAMS = 3f
        const val MIN_TOTAL_FAT_GRAMS = 15f
        const val MIN_PROTEIN_GRAMS = 10f
        const val MAX_SATURATED_FAT_GRAMS = 15f
        const val MIN_SODIUM_MG = 300f
        val notAllowedIngredients = listOf(
            "bread", "pasta", "rice", "potato", "sugar", "high fructose corn syrup",
            "corn", "wheat", "flour", "legume", "bean", "soda", "milk", "skim milk",
            "honey", "agave", "cereal", "oat", "quinoa", "yogurt", "juice",
        )
        val allowedFruits = listOf("strawberry", "blueberry", "raspberry", "blackberry")
    }
    fun isKetoFriendly(meal: Meal): Boolean {
        return isPassesKetoNutritionCheck(meal.nutrition) &&
                containsAllowedIngredients(meal)
    }

    private fun isPassesKetoNutritionCheck(nutrition: Nutrition): Boolean {
        return (nutrition.carbohydrates == null || nutrition.carbohydrates <= MAX_CARBOHYDRATES_GRAMS) &&
                (nutrition.sugar == null || nutrition.sugar <= MAX_SUGAR_GRAMS) &&
                (nutrition.totalFat == null || nutrition.totalFat >= MIN_TOTAL_FAT_GRAMS) &&
                (nutrition.protein == null || nutrition.protein >= MIN_PROTEIN_GRAMS) &&
                (nutrition.saturatedFat == null || nutrition.saturatedFat <= MAX_SATURATED_FAT_GRAMS) &&
                (nutrition.sodium == null || nutrition.sodium >= MIN_SODIUM_MG)
    }


    private fun containsAllowedIngredients(meal: Meal): Boolean {
        val hasNotAllowedIngredient = meal.ingredients.any { ingredient ->
            val lowerIngredient = ingredient.lowercase()
            notAllowedIngredients.any { it ->
                lowerIngredient.contains(it.lowercase())
            } && !isAllowedFruit(lowerIngredient)
        }

        return !hasNotAllowedIngredient
    }

    private fun isAllowedFruit(ingredient: String): Boolean {
        return allowedFruits.any { fruit -> ingredient.contains(fruit) }
    }

}