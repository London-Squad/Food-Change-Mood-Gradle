package data

import logic.MealsDataSource
import model.Meal
import model.Nutrition
import java.time.LocalDate

class FakeMealsDataSource : MealsDataSource {
    override fun getAllMeals(): List<Meal> {
        return listOf(
            Meal(
                id = 137739u,
                name = "arriba baked winter squash mexican style",
                minutes = 55u,
                contributorId = 47892u,
                submitted = LocalDate.parse("2005-09-16"),
                tags = listOf("60-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine", "preparation", "occasion", "north-american", "side-dishes", "vegetables", "mexican", "easy", "fall", "holiday-event", "vegetarian", "winter", "dietary", "christmas", "seasonal", "squash"),
                nutrition = Nutrition(51.5f, 0f, 13f, 0f, 2f, 0f, 4f),
                numberOfSteps = 11u,
                steps = listOf("make a choice and proceed with recipe", "depending on size of squash , cut into half or fourths", "remove seeds", "for spicy squash , drizzle olive oil or melted butter over each cut squash piece", "season with mexican seasoning mix ii", "for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece", "season with sweet mexican spice mix", "bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin", "be careful not to burn the squash especially if you opt to use sugar or butter", "if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking", "if desired , season with salt"),
                description = """autumn is my favorite time of year to cook! this recipe
    can be prepared either spicy or sweet, your choice!
    two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.""",
                ingredients = listOf("winter squash", "mexican seasoning", "mixed spice", "honey", "butter", "olive oil", "salt"),
                numberOfIngredients = 7u
            ),
            Meal(
                id = 31490u,
                name = "a bit different  breakfast pizza",
                minutes = 30u,
                contributorId = 26278u,
                submitted = LocalDate.parse("2002-06-17"),
                tags = listOf("30-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine", "preparation", "occasion", "north-american", "breakfast", "main-dish", "pork", "american", "oven", "easy", "kid-friendly", "pizza", "dietary", "northeastern-united-states", "meat", "equipment"),
                nutrition = Nutrition(173.4f, 18.0f, 0.0f, 17.0f, 22.0f, 35.0f, 1.0f),
                numberOfSteps = 9u,
                steps = listOf("preheat oven to 425 degrees f", "press dough into the bottom and sides of a 12 inch pizza pan", "bake for 5 minutes until set but not browned", "cut sausage into small pieces", "whisk eggs and milk in a bowl until frothy", "spoon sausage over baked crust and sprinkle with cheese", "pour egg mixture slowly over sausage and cheese", "s& p to taste", "bake 15-20 minutes or until eggs are set and crust is brown"),
                description = """this recipe calls for the crust to be prebaked a bit before adding ingredients. feel free to change sausage to ham or bacon. this warms well in the microwave for those late risers.""",
                ingredients = listOf("prepared pizza crust", "sausage patty", "eggs", "milk", "salt and pepper", "cheese"),
                numberOfIngredients = 6u
            ),
        )
    }

}