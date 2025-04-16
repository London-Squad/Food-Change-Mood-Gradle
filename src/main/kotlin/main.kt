import di.appModule
import di.presentationModule
import di.useCaseModule
import logic.MealSearchRepository
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import presentation.FoodChangeModeConsoleUI

fun main() {
    startKoin {
        modules(appModule, useCaseModule, presentationModule)
    }

    val repo: MealSearchRepository = getKoin().get()

    val results = repo.searchMeals("squh")
//    println(results)
    val ui = FoodChangeModeConsoleUI(getKoin().get())

    ui.start()
}