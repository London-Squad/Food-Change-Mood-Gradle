package logic

import model.Meal

class GetItalianFoodForLargeGroupUseCase(
    private val mealsDataSource: MealsDataSource
) {

    fun getItalianMealsForLargeGroup(): List<Meal> = mealsDataSource.getAllMeals().filter(::onlyItalianForLargeGroups)


    private fun onlyItalianForLargeGroups(meal: Meal): Boolean {
        return (
                meal.tags.contains(ITALIAN_TAG)
                        || meal.description.lowercase().contains(ITALY_KEYWORD)
                        || meal.name.lowercase().contains(ITALY_KEYWORD)
                )
                && meal.tags.contains(LARGE_GROUP_TAG)
    }


    companion object {
        const val LARGE_GROUP_TAG = "for-large-groups"
        const val ITALIAN_TAG = "italian"
        const val ITALY_KEYWORD = "italy"
    }
}