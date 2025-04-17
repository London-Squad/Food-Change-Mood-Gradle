package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.FoodChangeModeConsoleUI
import presentation.GetIraqiMealsView
import presentation.MealGuessGameView
import presentation.ViewUtil
import presentation.easyMeal.EasyMealView
import presentation.foodCulture.CountryFoodCultureView


val presentationModule = module {
    factory { ViewUtil() }
    factory { GetIraqiMealsView(get(), get()) }
    factory { MealGuessGameView(get()) }
    factory { EasyMealView(get()) }
    factory { CountryFoodCultureView(get()) }

    factoryOf(::FoodChangeModeConsoleUI)
}