package logic.search.byDate

import logic.IndexBuilder
import logic.MealsDataSource
import model.Meal
import java.time.LocalDate

class MealDateInvertedIndexBuilder(
    private val mealsDataSource: MealsDataSource
) : IndexBuilder<LocalDate, List<Int>> {

    private val index: Map<LocalDate, List<Int>>

    init {
        index = build(mealsDataSource.getAllMeals())
    }

    override fun getIndex(): Map<LocalDate, List<Int>> {
        return index
    }

    private fun build(meals: List<Meal>): Map<LocalDate, List<Int>> =
        meals.withIndex()
            .filter { (_, meal) -> meal.dateSubmitted != null }
            .groupBy(
                keySelector = { (_, meal) -> meal.dateSubmitted!! },
                valueTransform = { (idx, _) -> idx }
            )
}