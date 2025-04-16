package logic

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
            "honey", "agave", "cereal", "oat", "quinoa", "yogurt", "juice"
        )
        val allowedFruits = listOf("strawberry", "blueberry", "raspberry", "blackberry")
    }
    fun isPassesKetoNutritionCheck(nutrition:Nutrition):Boolean{
        return (nutrition.carbohydrates ?: 0f) <= MAX_CARBOHYDRATES_GRAMS &&
                (nutrition.sugar ?: 0f) <= MAX_SUGAR_GRAMS &&
                (nutrition.totalFat ?: 0f) >= MIN_TOTAL_FAT_GRAMS &&
                (nutrition.protein ?: 0f) >= MIN_PROTEIN_GRAMS &&
                (nutrition.saturatedFat ?: 0f) <= MAX_SATURATED_FAT_GRAMS &&
                (nutrition.sodium ?: 0f) >= MIN_SODIUM_MG
    }
}