package logic.util

import model.Meal

fun List<Meal>.getRandomMeals(count: Int): List<Meal> {
    return if (size <= count) this
    else {
        val indexList = indices.toMutableList()
        (0..<count).map {
            val randomIndex = indexList.random()
            indexList.remove(randomIndex)
            this[randomIndex]
        }
    }
}