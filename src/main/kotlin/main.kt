import data.search.*
import di.appModule
import di.useCaseModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
        modules(appModule, useCaseModule)
    }

    val service: MealSearchService = getKoin().get()

    val results = service.search("squh")
    println(results)
}