package logic

import model.Meal

interface IndexBuilder {
    fun build(meals: List<Meal>): Map<String, Set<Int>>
}