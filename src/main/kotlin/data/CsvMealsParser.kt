package data

import model.Meal
import model.Nutrition
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class CsvMealsParser {
    fun parseLine(line : String) : Meal {
        val inputStrings = splitInputs(line)
        return Meal(
            id = inputStrings[ColumnIndex.id].toIntOrNull(),
            name = inputStrings[ColumnIndex.name],
            minutes = inputStrings[ColumnIndex.minutes].toIntOrNull(),
            contributorId = inputStrings[ColumnIndex.contributorId].toIntOrNull(),
            submitted = LocalDate.parse(inputStrings[ColumnIndex.submitted]),
            tags = listStringToListOfStringsObj(inputStrings[ColumnIndex.tags]),
            nutrition = Nutrition(51.5f, 0f, 13f, 0f, 2f, 0f, 4f),
            numberOfSteps = 11,
            steps = listOf("make a choice and proceed with recipe", "depending on size of squash , cut into half or fourths", "remove seeds", "for spicy squash , drizzle olive oil or melted butter over each cut squash piece", "season with mexican seasoning mix ii", "for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece", "season with sweet mexican spice mix", "bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin", "be careful not to burn the squash especially if you opt to use sugar or butter", "if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking", "if desired , season with salt"),
            description = """autumn is my favorite time of year to cook! this recipe
can be prepared either spicy or sweet, your choice!
two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.""",
            ingredients = listOf("winter squash", "mexican seasoning", "mixed spice", "honey", "butter", "olive oil", "salt"),
            numberOfIngredients = 7
        )
    }

    private fun splitInputs(line: String) : List<String> {
        val inputStrings : MutableList<String> = mutableListOf("")
        var openQuot = false
        line.forEach { char ->
            if (char == ',' && !openQuot) inputStrings.add("")
            else if (char == '"') openQuot = !openQuot
            else inputStrings[inputStrings.lastIndex] = inputStrings[inputStrings.lastIndex] + char
        }
//        inputStrings.forEach{println(it)}
        return inputStrings
    }

    private fun listStringToListOfStringsObj(listString: String): List<String>? {
        if (listString == "") return null

        val result : MutableList<String> = mutableListOf("")
        var openQuot = false
        listString.forEach { char ->
            if (char == ',' && !openQuot ) result.add("")
            else if (char == '\'') openQuot = !openQuot
            else if (openQuot) result[result.lastIndex] = result[result.lastIndex] + char
        }
        return result
    }

//    private fun listStringToNutrition(listString: String): Nutrition {
//
//    }
}
// the first row in the csv file:
//
//arriba   baked winter squash mexican style,137739,55,47892,2005-09-16,"['60-minutes-or-less', 'time-to-make', 'course', 'main-ingredient', 'cuisine', 'preparation', 'occasion', 'north-american', 'side-dishes', 'vegetables', 'mexican', 'easy', 'fall', 'holiday-event', 'vegetarian', 'winter', 'dietary', 'christmas', 'seasonal', 'squash']","[51.5, 0.0, 13.0, 0.0, 2.0, 0.0, 4.0]",11,"['make a choice and proceed with recipe', 'depending on size of squash , cut into half or fourths', 'remove seeds', 'for spicy squash , drizzle olive oil or melted butter over each cut squash piece', 'season with mexican seasoning mix ii', 'for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece', 'season with sweet mexican spice mix', 'bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin', 'be careful not to burn the squash especially if you opt to use sugar or butter', 'if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking', 'if desired , season with salt']","autumn is my favorite time of year to cook! this recipe
//can be prepared either spicy or sweet, your choice!
//two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.","['winter squash', 'mexican seasoning', 'mixed spice', 'honey', 'butter', 'olive oil', 'salt']",7
//
// name, arriba   baked winter squash mexican style
// id, 137739
// minutes,55
// contributor_id,47892
// submitted,2005-09-16
// tags,"['60-minutes-or-less', 'time-to-make', 'course', 'main-ingredient', 'cuisine', 'preparation', 'occasion', 'north-american', 'side-dishes', 'vegetables', 'mexican', 'easy', 'fall', 'holiday-event', 'vegetarian', 'winter', 'dietary', 'christmas', 'seasonal', 'squash']"
// nutrition,"[51.5, 0.0, 13.0, 0.0, 2.0, 0.0, 4.0]"
// n_steps,11
// steps,"['make a choice and proceed with recipe', 'depending on size of squash , cut into half or fourths', 'remove seeds', 'for spicy squash , drizzle olive oil or melted butter over each cut squash piece', 'season with mexican seasoning mix ii', 'for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece', 'season with sweet mexican spice mix', 'bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin', 'be careful not to burn the squash especially if you opt to use sugar or butter', 'if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking', 'if desired , season with salt']"
// description,"autumn is my favorite time of year to cook! this recipe
//// can be prepared either spicy or sweet, your choice!
//// two of my posted mexican-inspired seasoning mix recipes are offered as suggestions."
// ingredients,"['winter squash', 'mexican seasoning', 'mixed spice', 'honey', 'butter', 'olive oil', 'salt']"
// n_ingredients,7
// ,,,,,,,,,,,

// Meal(
//            "arriba baked winter squash mexican style",
//            137739,
//            55,
//            47892,
//            Date(), // "2005-09-16",
//            listOf("60-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine", "preparation", "occasion", "north-american", "side-dishes", "vegetables", "mexican", "easy", "fall", "holiday-event", "vegetarian", "winter", "dietary", "christmas", "seasonal", "squash"),
//            listOf(51.5f, 0f, 13f, 0f, 2f, 0f, 4f),
//            11,
//            listOf("make a choice and proceed with recipe", "depending on size of squash , cut into half or fourths", "remove seeds", "for spicy squash , drizzle olive oil or melted butter over each cut squash piece", "season with mexican seasoning mix ii", "for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece", "season with sweet mexican spice mix", "bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin", "be careful not to burn the squash especially if you opt to use sugar or butter", "if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking", "if desired , season with salt"),
//            """autumn is my favorite time of year to cook! this recipe
//can be prepared either spicy or sweet, your choice!
//two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.""",
//            listOf("winter squash", "mexican seasoning", "mixed spice", "honey", "butter", "olive oil", "salt"),
//            7
//        )