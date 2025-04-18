package logic.util

import model.Meal

fun List<Meal>.getRandomMeals(count: Int, condition: (Meal) -> Boolean): List<Meal> {
    return this.filter(condition).run {
        if (size < count) this
        else shuffled().slice(0..<count)
    }
}