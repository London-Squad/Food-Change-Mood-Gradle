package presentation.utils

import model.Meal

class UIMealPrinter(
    private val cliPrinter: CLIPrinter
) {
    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)
    
    fun printMealDetails(meal: Meal) {
        printHeader("Meal Details")
        printMealBasicInfo(meal)
        printDescription(meal)
        printIngredients(meal)
        printTags(meal)
        printNutrition(meal)
        printSteps(meal)
    }

    private fun printMealBasicInfo(meal: Meal) {
        printTextWithinWidth(    "Name:            ${meal.name}")
        printLn(    "ID:              ${meal.id}")
        if (meal.dateSubmitted != null) {
            printLn("Add date:        ${meal.dateSubmitted}")
        }
        if (meal.minutes != null) {
            printLn("Time to prepare: ${meal.minutes} minutes")
        }
        printLn(getThinHorizontal())
    }

    private fun printDescription(meal: Meal) {
        printLn("Description: ")
        printTextWithinWidth(meal.description, indent = 4)
        printLn(getThinHorizontal())
    }

    private fun printIngredients(meal: Meal) {
        printLn("Ingredients")
        meal.ingredients.forEachIndexed { ingredientIndex, ingredient ->
            printLn("    ${ingredientIndex + 1}. $ingredient")
        }
        printLn(getThinHorizontal())
    }

    private fun printTags(meal: Meal) {
        if (meal.tags.isNotEmpty()) {
            printLn("Tags: ")
            printTextWithinWidth(meal.tags.toString(), indent = 4)
        }
        printLn(getThinHorizontal())
    }

    private fun printNutrition(meal: Meal) {
        printLn("Nutrition: ")
        printLn("    calories: ${meal.nutrition.calories ?: "unknown"}")
        printLn("    totalFat: ${meal.nutrition.totalFat ?: "unknown"}")
        printLn("    sugar: ${meal.nutrition.sugar ?: "unknown"}")
        printLn("    sodium: ${meal.nutrition.sodium ?: "unknown"}")
        printLn("    protein: ${meal.nutrition.protein ?: "unknown"}")
        printLn("    saturatedFat: ${meal.nutrition.saturatedFat ?: "unknown"}")
        printLn("    carbohydrates: ${meal.nutrition.carbohydrates ?: "unknown"}")
        printLn(getThinHorizontal())
    }

    private fun printSteps(meal: Meal) {
        printLn("Steps to prepare")
        meal.steps.forEachIndexed { stepIndex, step ->
            printTextWithinWidth("${stepIndex + 1}. $step", indent = 4)
        }
        printLn(getThinHorizontal())
    }

    private fun String.duplicate(numberOfDuplication: Int) =
        List(numberOfDuplication){this}.joinToString(separator = "")

    fun getThickHorizontal() =
        THICK_HORIZONTAL_ELEMENT.duplicate(VIEW_WIDTH)

    fun getThinHorizontal() =
        THIN_HORIZONTAL_ELEMENT.duplicate(VIEW_WIDTH)

    private fun String.center(): String {
        val emptySpaceOnEachSide = (VIEW_WIDTH - this.length) / 2
        if (emptySpaceOnEachSide <= 0) return this
        return " ".duplicate(emptySpaceOnEachSide) + this + " ".duplicate(emptySpaceOnEachSide)
    }

    fun printHeader(headerText: String) {
        printLn("\n" + getThickHorizontal())
        printLn(headerText.center())
        printLn(getThickHorizontal() + "\n")
    }

    fun printTextWithinWidth(text: String, indent: Int = 0) {
        text.chunked(VIEW_WIDTH - indent).forEach {
            printLn(
                " ".duplicate(indent) + it
            )
        }
    }

    companion object {
        const val VIEW_WIDTH = 70
        const val THICK_HORIZONTAL_ELEMENT = "="
        const val THIN_HORIZONTAL_ELEMENT = "-"
    }
}