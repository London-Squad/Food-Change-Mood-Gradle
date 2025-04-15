package model
import java.util.Date

data class Meal(
    val name: String,
    val id: Int,
    val minutes: Int,
    val contributorId: Int,
    val submitted: Date,
    val tags: List<String>,
    val nutrition: List<Float>,
    val nSteps: Int,
    val steps: List<String>,
    val description: String,
    val ingredients: List<String>,
    val nIngredients: Int,
)

// the first row in the csv file:
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
// "arriba baked winter squash mexican style",
// 137739,
// 55,
// 47892,
// 2005-09-16,
// ['60-minutes-or-less', 'time-to-make', 'course', 'main-ingredient', 'cuisine', 'preparation', 'occasion', 'north-american', 'side-dishes', 'vegetables', 'mexican', 'easy', 'fall', 'holiday-event', 'vegetarian', 'winter', 'dietary', 'christmas', 'seasonal', 'squash']"
// [51.5, 0.0, 13.0, 0.0, 2.0, 0.0, 4.0]
// 11
// ["make a choice and proceed with recipe", "depending on size of squash , cut into half or fourths", "remove seeds", "for spicy squash , drizzle olive oil or melted butter over each cut squash piece", "season with mexican seasoning mix ii", "for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece", "season with sweet mexican spice mix", "bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin", "be careful not to burn the squash especially if you opt to use sugar or butter", "if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking", "if desired , season with salt"]
// """autumn is my favorite time of year to cook! this recipe
// can be prepared either spicy or sweet, your choice!
// two of my posted mexican-inspired seasoning mix recipes are offered as suggestions."""
// ["winter squash", "mexican seasoning", "mixed spice", "honey", "butter", "olive oil", "salt"],
// 7
// )