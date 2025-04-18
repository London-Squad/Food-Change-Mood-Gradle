package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.*
import presentation.FoodChangeModeConsoleUI
import presentation.GetIraqiMealsView
import presentation.ViewUtil
import presentation.easyMeal.EasyMealView


val presentationModule = module {
    factory { ViewUtil() }
    factory { GetIraqiMealsView(get(), get()) }
    factory { GuessMealGameView(get()) }
    factory { EasyMealView(get()) }

    factoryOf(::FoodChangeModeConsoleUI)
}
