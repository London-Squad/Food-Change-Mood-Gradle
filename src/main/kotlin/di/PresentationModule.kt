package di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import presentation.*


val presentationModule = module {
    single { ViewUtil() }
    single { GetIraqiMealsView(get(), get()) }
    single { MealGuessGameView(get()) }
    single { MealSearchByNameView(get(named("byName")), get()) }

    single {
        FoodChangeModeConsoleUI(
            getIraqiMealsView = get(),
            mealGuessGameView = get(),
            mealSearchByNameView = get()
        )
    }
}