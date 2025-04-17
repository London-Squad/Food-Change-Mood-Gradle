package logic

import model.Meal
import model.Nutrition

class KetoFriendlyValidator {
    private companion object {
        const val MAX_CARBOHYDRATES_GRAMS = 5f
        const val MAX_SUGAR_GRAMS = 1f
        const val MIN_TOTAL_FAT_GRAMS = 20f
        const val MIN_PROTEIN_GRAMS = 15f
        const val MAX_SATURATED_FAT_GRAMS = 10f
        const val MIN_SODIUM_MG = 500f
        val notAllowedIngredients = listOf(
            "bread", "pasta", "rice", "potato", "sugar", "high fructose corn syrup",
            "corn", "wheat", "flour", "legume", "bean", "soda", "milk", "skim milk",
            "honey", "agave", "cereal", "oat", "quinoa", "yogurt", "juice","fruit",
        )
        val allowedFruits = listOf("strawberry", "blueberry", "raspberry", "blackberry")
    }

    fun isKetoFriendly(meal: Meal): Boolean {
        return isPassesKetoNutritionCheck(meal.nutrition) &&
                containsAllowedIngredients(meal)
    }

    private fun isPassesKetoNutritionCheck(nutrition: Nutrition): Boolean {
        return (nutrition.carbohydrates ?: 0f) <= MAX_CARBOHYDRATES_GRAMS &&
                (nutrition.sugar ?: 0f) <= MAX_SUGAR_GRAMS &&
                (nutrition.totalFat ?: 0f) >= MIN_TOTAL_FAT_GRAMS &&
                (nutrition.protein ?: 0f) >= MIN_PROTEIN_GRAMS &&
                (nutrition.saturatedFat ?: 0f) <= MAX_SATURATED_FAT_GRAMS &&
                (nutrition.sodium ?: 0f) >= MIN_SODIUM_MG
    }

    private fun containsAllowedIngredients(meal: Meal): Boolean {
      val hasNotAllowedIngredients:Boolean =  meal.ingredients.any{ingredient->
           val lowerIngredient = ingredient.lowercase()
           notAllowedIngredients.any{it-> lowerIngredient.contains(it.lowercase())} && !isAllowedFruites(lowerIngredient)
       }
        return !hasNotAllowedIngredients
    }
    private fun isAllowedFruites(ingredient:String):Boolean{
        return allowedFruits.any{fruite->ingredient.contains(fruite)}
    }
}