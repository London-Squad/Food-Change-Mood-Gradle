package logic

class KetoFriendlyValidator {
    companion object {
        private val notAllowedIngredients = listOf(
            "bread", "pasta", "rice", "potato", "sugar", "high fructose corn syrup",
            "corn", "wheat", "flour", "legume", "bean", "soda", "milk", "skim milk",
            "honey", "agave", "cereal", "oat", "quinoa", "yogurt", "juice"
        )
        private val allowedFruits = listOf("strawberry", "blueberry", "raspberry", "blackberry")
    }
}