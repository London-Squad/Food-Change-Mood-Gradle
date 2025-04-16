package logic

import model.Meal

class GymHelper {

    fun HelpingGymMembers(meals: List<Meal>){

        println("Enter desired calories: ")
        val CaloriesFromUser = readln().toFloat()
        println("Enter desired protein: ")
        val ProteinFromUser = readln().toFloat()
        println("Enter approximate those values. (example : enter 10 for 10%  \nnote 10 -> 10% is a default value) ")
        val approximate = readlnOrNull()
            ?.toDoubleOrNull()
            ?.let { it / 100 }
            ?: 0.1

        val minCalories = CaloriesFromUser * (1 - approximate)
        val maxCalories = CaloriesFromUser * (1 + approximate)
        val minProtein = ProteinFromUser * (1 - approximate)
        val maxProtein = ProteinFromUser * (1 + approximate)


        val validMeals = meals.filter { isHighQualityMeal(it) }
        val matchingMeals = validMeals.filter { meal ->
                    meal.nutrition.calories!! in minCalories..maxCalories
                    &&
                    meal.nutrition.protein!! in minProtein..maxProtein
        }

        println("The meal is :")
        matchingMeals.forEach {
            println("- ${it.name} (Calories: ${it.nutrition.calories}, Protein: ${it.nutrition.protein})") }
        }

    fun isHighQualityMeal(meal: Meal): Boolean {
        if (meal.nutrition == null) return false
        if (meal.nutrition.protein == null ) return false
        if (meal.nutrition.protein <= 0.0f ) return false
        if (meal.nutrition.calories == null ) return false
        if (meal.nutrition.calories <= 0.0f ) return false
        return true
    }
    }


