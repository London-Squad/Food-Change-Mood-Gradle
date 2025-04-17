import di.appModule
import di.presentationModule
import di.useCaseModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import presentation.FoodChangeModeConsoleUI


fun main() {
    startKoin {
        modules(appModule, useCaseModule, presentationModule)
    }

    val ui : FoodChangeModeConsoleUI = getKoin().get()
    ui.start()
}