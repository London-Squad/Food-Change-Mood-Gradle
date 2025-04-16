package logic.search.byDate

import logic.IndexBuilder
import logic.MealSearchRepository
import model.Meal
import utils.InvalidDateFormatException
import utils.NoMealsFoundException
import java.time.LocalDate
import java.time.format.DateTimeParseException

    class MealSearchByDateRepositoryImpl(
        private val meals: List<Meal>,
        private val dateIndexBuilder: IndexBuilder<LocalDate,List<Int>>
    ) : MealSearchRepository<List<Pair<Int, String>>> {
        private val dateIndex: Map<LocalDate, List<Int>> by lazy { dateIndexBuilder.build(meals) }
        private val idIndex: Map<Int, Int> by lazy {
            meals.withIndex().associate { (idx, meal) -> meal.id to idx }
        }

        override fun searchMeals(keyword: String): List<Pair<Int, String>> {
            val parsedDate = try {
                LocalDate.parse(keyword)
            } catch (e: DateTimeParseException) {
                throw InvalidDateFormatException("Invalid date format: '$keyword'. Use yyyy-MM-dd (e.g., 2023-04-16).")
            }

            val indices = dateIndex[parsedDate]
                ?: throw NoMealsFoundException("No meals found for date: $parsedDate")

            return indices.mapNotNull { idx ->
                meals.getOrNull(idx)?.let { meal ->
                    meal.id to meal.name
                }
            }
        }

         fun getMealDetails(id: Int): Meal {
            val idx = idIndex[id]
                ?: throw NoMealsFoundException("No meal found with ID: $id")
            return meals[idx]
        }
    }