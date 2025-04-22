package logic.gymHelper
import logic.MealsDataSource
import model.Meal
class GymHelperUseCase(
    private val mealsDataSource: MealsDataSource
) {
    fun getGymMembersMeals(
        caloriesUserInput: Float,
        proteinUserInput: Float,
        approximatePercent: Double =defaultApproximatePercent
    ): List<Meal> {
        val calorieRange = getRange(caloriesUserInput, approximatePercent)
        val proteinRange = getRange(proteinUserInput, approximatePercent)

        return mealsDataSource.getAllMeals()
            .filter { isHighQualityMeal(it) }
            .filter { isWithinRange(it, calorieRange, proteinRange) }
    }
    private fun getRange(value: Float, percent: Double): Pair<Float, Float> {
        val min = value * (1 - percent).toFloat()
        val max = value * (1 + percent).toFloat()
        return Pair(min, max)
    }
    private fun isWithinRange(meal: Meal, calorieRange: Pair<Float, Float>, proteinRange: Pair<Float, Float>): Boolean {
        val calories = meal.nutrition.calories
        val protein = meal.nutrition.protein

        return calories != null && protein != null &&
                calories in calorieRange.first..calorieRange.second &&
                protein in proteinRange.first..proteinRange.second
    }

    private fun isHighQualityMeal(meal: Meal): Boolean {
        val protein = meal.nutrition.protein
        val calories = meal.nutrition.calories
        return protein != null && protein > 0f &&
                calories != null && calories > 0f
    }
    companion object{
        val  defaultApproximatePercent=0.1
    }
}