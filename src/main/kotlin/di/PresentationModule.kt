package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.*

import presentation.*
import presentation.easyMeal.EasyMealView


val presentationModule = module {
    factory { ViewUtil() }
    factory { SuggestSweetWithoutEggView(get(), get()) }
    factory { GetIraqiMealsView(get(), get()) }
    factory { MealGuessGameView(get()) }
    factory { EasyMealView(get()) }
    factory { IngredientGameView(get()) }
    single {GetHealtyFastFoodMealsView(get(),get())}


    factoryOf(::FoodChangeModeConsoleUI)
}
