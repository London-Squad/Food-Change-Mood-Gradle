package presentation.mealDetails

import model.Meal
import presentation.BaseView

class MealDetailsView(
    private val meal: Meal
) : BaseView {

    override fun start() {
        printHeader()
        printIngredients()
        printNutrition()
        println()
    }

    private fun printHeader() {
        println(
            """
            ------------------------------------------
                           Easy Meal Details         
            ------------------------------------------  
            name: ${meal.name}name
            time to prepare: ${meal.minutes} minutes
            """.trimIndent()
        )
        println()
    }

    private fun printIngredients() {
        println(
            """
            ------------------------------------------
                            ingredients         
            ------------------------------------------   
            """.trimIndent()
        )
        if (meal.ingredients.isEmpty()) println("There is no ingredients for this meal")
        else meal.ingredients.forEach(::println)
        println()
    }

    private fun printNutrition() {
        meal.nutrition.let { nutrition ->
            println(
                """
                ------------------------------------------
                               Nutrition         
                ------------------------------------------   
                """.trimIndent()
            )
            nutrition.calories?.let { println("calories: $it") }
            nutrition.sodium?.let { println("sodium: $it") }
            nutrition.sugar?.let { println("sugar: $it") }
            nutrition.protein?.let { println("protein: $it") }
            nutrition.totalFat?.let { println("totalFat: $it") }
            nutrition.saturatedFat?.let { println("saturatedFat: $it") }
            nutrition.carbohydrates?.let { println("carbohydrates: $it") }
        }
    }
}