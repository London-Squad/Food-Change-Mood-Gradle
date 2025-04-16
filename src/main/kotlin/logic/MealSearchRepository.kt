package logic


interface MealSearchRepository<K> {
    fun searchMeals(keyword: String): K
}