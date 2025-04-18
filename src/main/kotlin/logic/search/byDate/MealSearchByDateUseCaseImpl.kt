package logic.search.byDate

import logic.search.IndexBuilder
import logic.search.MealSearchUseCase
import logic.MealsDataSource
import model.Meal
import logic.util.InvalidDateFormatException
import logic.util.NoMealsFoundException
import java.time.LocalDate
import java.time.format.DateTimeParseException

class MealSearchByDateUseCaseImpl(
    private val mealsDataSource: MealsDataSource,
    private val dateIndexBuilder: IndexBuilder<LocalDate, List<Int>>,
    private val idIndexBuilder: IndexBuilder<Int, Int>
) : MealSearchUseCase<List<Pair<Int, String>>> {

    override fun searchMeals(keyword: String): List<Pair<Int, String>> {
        val parsedDate = try {
            LocalDate.parse(keyword)
        } catch (e: DateTimeParseException) {
            throw InvalidDateFormatException("Invalid date format: '$keyword'. Use yyyy-MM-dd (e.g., 2023-04-16).")
        }

        val indices = dateIndexBuilder.getIndex()[parsedDate]
            ?: throw NoMealsFoundException("No meals found for date: $parsedDate")

        return indices.mapNotNull { idx ->
            mealsDataSource.getAllMeals().getOrNull(idx)?.let { meal ->
                meal.id to meal.name
            }
        }
    }

    fun getMealDetails(id: Int): Meal {
        val idx = idIndexBuilder.getIndex()[id]
            ?: throw NoMealsFoundException("No meal found with ID: $id")
        return mealsDataSource.getAllMeals()[idx]
    }
}