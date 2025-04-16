package logic


interface MealSearchUseCase<K> {
    fun searchMeals(keyword: String): K
}