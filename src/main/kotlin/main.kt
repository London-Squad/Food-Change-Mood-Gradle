import di.appModule
import di.useCaseModule
import logic.MealSearchRepository
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import presentation.FoodChangeModeConsoleUI

fun main() {
    startKoin {
        modules(appModule, useCaseModule)
    }

    val ui : FoodChangeModeConsoleUI = getKoin().get()
    ui.start()
}