package logic.util

import model.Meal

fun List<Meal>.getRandomMeals(condition: (Meal) -> Boolean, count: Int): List<Meal> {
    return this.filter(condition).run {
        if (size < count) this
        else shuffled().slice(0..<count)
    }
}