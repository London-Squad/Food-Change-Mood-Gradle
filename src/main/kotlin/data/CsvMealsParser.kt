package data

import model.Meal
import java.util.Date

class CsvMealsParser {
    fun parseLine(line : String) : Meal {
        return Meal(
            "arriba baked winter squash mexican style",
            137739,
            55,
            47892,
            Date(), // "2005-09-16",
            listOf("60-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine", "preparation", "occasion", "north-american", "side-dishes", "vegetables", "mexican", "easy", "fall", "holiday-event", "vegetarian", "winter", "dietary", "christmas", "seasonal", "squash"),
            listOf(51.5f, 0f, 13f, 0f, 2f, 0f, 4f),
            11,
            listOf("make a choice and proceed with recipe", "depending on size of squash , cut into half or fourths", "remove seeds", "for spicy squash , drizzle olive oil or melted butter over each cut squash piece", "season with mexican seasoning mix ii", "for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece", "season with sweet mexican spice mix", "bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin", "be careful not to burn the squash especially if you opt to use sugar or butter", "if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking", "if desired , season with salt"),
            """autumn is my favorite time of year to cook! this recipe
can be prepared either spicy or sweet, your choice!
two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.""",
            listOf("winter squash", "mexican seasoning", "mixed spice", "honey", "butter", "olive oil", "salt"),
            7
        )
    }
}