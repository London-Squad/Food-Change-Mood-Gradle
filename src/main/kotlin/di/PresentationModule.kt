package di

import org.koin.dsl.module
import presentation.*


val presentationModule = module {
    single { ViewUtil() }
    single { GetIraqiMealsView(get(), get()) }
    single { GuessMealGameView(get()) }
    single { GetMealsContainPotatoView(get(), get()) }

    single {
        FoodChangeModeConsoleUI(
            get(),
            get(),
            get()
        )
    }
}
