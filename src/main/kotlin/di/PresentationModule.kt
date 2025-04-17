package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.FoodChangeModeConsoleUI
import presentation.GetIraqiMealsView
import presentation.MealGuessGameView
import presentation.ViewUtil
import presentation.easyMeal.EasyMealView


val presentationModule = module {
    factory { ViewUtil() }
    factory { GetIraqiMealsView(get(), get()) }
    factory { MealGuessGameView(get()) }
    factory { EasyMealView(get()) }

    factoryOf(::FoodChangeModeConsoleUI)
}