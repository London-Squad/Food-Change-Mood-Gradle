package di

import org.koin.dsl.module
import presentation.FoodChangeModeConsoleUI
import presentation.GetIraqiMealsView
import presentation.ViewUtil


val presentationModule = module {
    single { ViewUtil() }
    single{GetIraqiMealsView(get(), get())}

    single {
        FoodChangeModeConsoleUI(
            get()
        )
    }
}
