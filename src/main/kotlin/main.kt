import di.appModule
import di.useCaseModule
import logic.MealSearchRepository
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
        modules(appModule, useCaseModule)
    }

    val repo: MealSearchRepository = getKoin().get()

    val results = repo.searchMeals("squh")
    println(results)
}