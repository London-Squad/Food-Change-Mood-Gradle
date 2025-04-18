package logic.search


interface MealSearchUseCase<K> {
    fun searchMeals(keyword: String): K
}