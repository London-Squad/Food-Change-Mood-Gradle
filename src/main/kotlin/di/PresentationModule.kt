package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.*
import presentation.easyMeal.EasyMealView


val presentationModule = module {
    factory { ViewUtil() }
    factory { IraqiMealsView(get(), get()) }
    factory { MealGuessGameView(get()) }
    factory { EasyMealView(get()) }
    factory { ItalianFoodForLargeGroupView(get(), get()) }

    factoryOf(::FoodChangeModeConsoleUI)
}