import di.appModule
import di.presentationModule
import di.useCaseModule
import logic.MealSearchUseCase
import model.Meal
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin
import presentation.FoodChangeModeConsoleUI

fun main() {
    startKoin {
        modules(appModule, useCaseModule, presentationModule)
    }

    val repo1: MealSearchUseCase<List<Pair<Int, String>>> = getKoin().get(named("byDate"))

    val repo2: MealSearchUseCase<List<Meal>> = getKoin().get(named("byName"))

    val results = repo2.searchMeals("2023-04-16")
    println(results)
    val ui : FoodChangeModeConsoleUI = getKoin().get()
    ui.start()
}