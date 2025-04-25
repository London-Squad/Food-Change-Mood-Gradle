package logic.gymHelperTest.testData
import model.Meal
import model.Nutrition
import java.time.LocalDate

object fakeGymMeals {
  
    val desiredMeal = Meal(
        id = 101,
        name = "Chicken Breast with Veggies",
        minutes = 15,
        dateSubmitted = LocalDate.parse("2025-04-24"),
        tags = listOf("high protein", "healthy", "low fat"),
        nutrition = Nutrition(250f, 50f, 5f, 10f, 1f, 3f, 2f), 
        steps = listOf("Grill chicken", "Prepare veggies", "Mix together"),
        description = "A healthy and high-protein meal for muscle gain.",
        ingredients = listOf("Chicken breast", "Spinach", "Olive oil")
    )

    // وجبة تحتوي على كاربوهيدرات عالية للطاقة
    val energyMeal = Meal(
        id = 102,
        name = "Pasta with Tomato Sauce",
        minutes = 20,
        dateSubmitted = LocalDate.parse("2025-04-24"),
        tags = listOf("high carbs", "energy", "vegetarian"),
        nutrition = Nutrition(500f, 70f, 10f, 20f, 3f, 8f, 4f),
        steps = listOf("Boil pasta", "Prepare sauce", "Mix together"),
        description = "Great for a quick energy boost, especially post-workout.",
        ingredients = listOf("Pasta", "Tomato sauce", "Parmesan")
    )

    
    val lowFatMeal = Meal(
        id = 103,
        name = "Salmon with Asparagus",
        minutes = 25,
        dateSubmitted = LocalDate.parse("2025-04-24"),
        tags = listOf("low fat", "weight loss", "high protein"),
        nutrition = Nutrition(200f, 45f, 10f, 15f, 4f, 10f, 3f),
        steps = listOf("Grill salmon", "Steam asparagus", "Serve together"),
        description = "A low-fat, high-protein meal perfect for weight loss.",
        ingredients = listOf("Salmon", "Asparagus", "Lemon")
    )

    val incompleteMeal = Meal(
        id = 104,
        name = "Incomplete Meal",
        minutes = 0,
        dateSubmitted = LocalDate.parse("2025-04-24"),
        tags = listOf(),
        nutrition = Nutrition(null, null, null, null, null, null, null),
        steps = listOf(),
        description = "",
        ingredients = listOf()
    )
    val incompleteMealNoCalories  = Meal(
        id = 104,
        name = "incompleteMealNoCalories ",
        minutes = 0,
        dateSubmitted = LocalDate.parse("2025-04-24"),
        tags = listOf(),
        nutrition = Nutrition(null, 56f, 55f, 5f, 20f, 02f, 88f),
        steps = listOf(),
        description = "",
        ingredients = listOf()
    )
    val incompleteMealNoProtein  = Meal(
        id = 104,
        name = "incompleteMealNoCalories ",
        minutes = 0,
        dateSubmitted = LocalDate.parse("2025-04-24"),
        tags = listOf(),
        nutrition = Nutrition(null, 56f, 55f, 5f, null, 02f, 88f),
        steps = listOf(),
        description = "",
        ingredients = listOf()
    )
   
    val allMeals = listOf(desiredMeal, energyMeal, lowFatMeal, incompleteMeal,incompleteMealNoCalories,incompleteMealNoProtein)
    val invalidMeals = listOf(incompleteMeal)
}
