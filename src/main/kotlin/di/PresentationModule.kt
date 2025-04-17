package di

import org.koin.dsl.module
import presentation.FoodChangeModeConsoleUI
import presentation.GetIraqiMealsView
import presentation.IngredientGameView
import presentation.ViewUtil


val presentationModule = module {
    single { ViewUtil() }
    single { GetIraqiMealsView(get(), get()) }
    single { IngredientGameView(get()) }

    single {
        FoodChangeModeConsoleUI(
            get(),
            get()
        )
    }
}
