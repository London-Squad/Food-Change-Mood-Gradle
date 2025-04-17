package logic

import model.Meal

class GymHelperUseCase(
    private val mealsDataSource: MealsDataSource
) {

    fun getGymMembersMeals(): List<Meal> {
        val allMeals = mealsDataSource.getAllMeals()
        val highQualityDate = mutableListOf<Meal>()

        allMeals.forEach { currentMeal ->
            if (isHighQualityMeal(currentMeal))
                highQualityDate.add(currentMeal)
        }

        val CaloriesFromUser = getValidFloatInput("Enter desired calories:")
        val ProteinFromUser = getValidFloatInput("Enter desired protein:")
        println("Enter approximate those values. (example : enter 10 for 10%  \nnote 10 -> 10% is a default value) ")
        val approximate = readlnOrNull()
            ?.toDoubleOrNull()
            ?.let {
                if(it<0){it*-1}
                it / 100 }
            ?: 0.1


            val minCalories = CaloriesFromUser * (1 - approximate)
            val maxCalories = CaloriesFromUser * (1 + approximate)
            val minProtein = ProteinFromUser * (1 - approximate)
            val maxProtein = ProteinFromUser * (1 + approximate)

            val matchingMeals = highQualityDate.filter { it ->
                       it.nutrition.calories!! in minCalories..maxCalories
                        ||
                        it.nutrition.protein!! in minProtein..maxProtein
            }
            if (matchingMeals.isEmpty()) {
                println("Sorry I didn't found what you need")
            }
                return matchingMeals



        }
    }

    fun isHighQualityMeal(meal: Meal): Boolean {
        if (meal.nutrition == null
            || meal.nutrition.protein == null || meal.nutrition.protein <= 0.0f
            || meal.nutrition.calories == null || meal.nutrition.calories <= 0.0f
        ) {
            return false
        } else {
            return true
        }
    }



fun getValidFloatInput(prompt: String): Float {
    while (true) {
        print("$prompt ")
        val input = readlnOrNull()
        val number = input?.toFloatOrNull()
        if (number != null && number >= 0 ) return number
        println("❌ Invalid input. Please enter a valid number.")
    }
}
