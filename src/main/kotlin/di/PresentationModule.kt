package di

import org.koin.dsl.module
import presentation.*


val presentationModule = module {
    single { ViewUtil() }
    single { GetIraqiMealsView(get(), get()) }
    single { MealGuessGameView(get()) }
    single {GetHealtyFastFoodMealsView(get(),get())}

    single {
        FoodChangeModeConsoleUI(
            get(),
            get(),
            get()
        )
    }
}
