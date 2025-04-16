import di.appModule
import di.useCaseModule
import logic.MealSearchUseCase
import model.Meal
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
        modules(appModule, useCaseModule)
    }

    val repo1: MealSearchUseCase<List<Pair<Int, String>>> = getKoin().get()

    val repo2: MealSearchUseCase<List<Meal>> = getKoin().get()

    val results = repo1.searchMeals("2023-04-16")
    println(results)
}