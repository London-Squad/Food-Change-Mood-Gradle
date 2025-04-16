import di.appModule
import di.useCaseModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import presentation.FoodChangeModeConsoleUI
import presentation.easyMeal.EasyMealView

fun main() {
    startKoin {
        modules(appModule, useCaseModule)
    }

    val easyMealView = EasyMealView(getKoin().get())
    FoodChangeModeConsoleUI(easyMealView).apply {
        start()
    }
}