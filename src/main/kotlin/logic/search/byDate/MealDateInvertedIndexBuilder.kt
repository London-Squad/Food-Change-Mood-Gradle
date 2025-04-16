package logic.search.byDate

import logic.IndexBuilder
import model.Meal
import java.time.LocalDate

class MealDateInvertedIndexBuilder : IndexBuilder<LocalDate,List<Int>> {
    override fun build(meals: List<Meal>): Map<LocalDate, List<Int>> =
        meals.withIndex()
            .filter { (_, meal) -> meal.dateSubmitted != null }
            .groupBy(
                keySelector = { (_, meal) -> meal.dateSubmitted!! },
                valueTransform = { (idx, _) -> idx }
            )
}
