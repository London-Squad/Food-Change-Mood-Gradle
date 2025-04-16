package di

import org.koin.dsl.module
import presentation.GetIraqiMealsView


val presentationModule = module {
    single { GetIraqiMealsView(get()) }
}
