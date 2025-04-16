package presentation

import model.Meal

class ViewUtil {
    fun printMeal(meal: Meal) {
        println()
        println("---------------------------------------------")
        println("               Meal Information              ")
        println("---------------------------------------------")
        println()
        println("Name: ${meal.name}")
        println("ID: ${meal.id}")
        println()
        println("Description: ")
        printTextWithinWidth(meal.description, indent = 4)
        println()
        println("Ingredients")
        meal.ingredients.forEachIndexed { ingredientIndex, ingredient ->
            println("    ${ingredientIndex+1}. $ingredient")
        }
        if (meal.minutes != null) {
            println()
            println("Time to prepare: ${meal.minutes} minutes")
        }
        if (meal.dateSubmitted != null) {
            println()
            println("Add date: ${meal.dateSubmitted}")
        }
        if (meal.tags.isNotEmpty()) {
            println()
            println("Tags: ")
            printTextWithinWidth(meal.tags.toString(), indent = 5)
            println()
        }
        println("Nutrition: ")

        println("    calories: ${meal.nutrition.calories?: "unknown"}")
        println("    totalFat: ${meal.nutrition.totalFat?: "unknown"}")
        println("    sugar: ${meal.nutrition.sugar?: "unknown"}")
        println("    sodium: ${meal.nutrition.sodium?: "unknown"}")
        println("    protein: ${meal.nutrition.protein?: "unknown"}")
        println("    saturatedFat: ${meal.nutrition.saturatedFat?: "unknown"}")
        println("    carbohydrates: ${meal.nutrition.carbohydrates?: "unknown"}")

        println()
        println("Steps to prepare")
        meal.steps.forEachIndexed { stepIndex, step ->
            printTextWithinWidth("${stepIndex+1}. $step", indent = 6)
        }

        println("---------------------------------------------")
    }

    fun printTextWithinWidth(text: String, indent: Int = 0) {
        text.chunked(VIEW_WIDTH).forEach {
            println(
                List(indent){""}.joinToString(" ") + it
            )
        }
    }

    companion object {
        const val VIEW_WIDTH = 70
    }
}