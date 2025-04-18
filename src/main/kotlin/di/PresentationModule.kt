package di

import org.koin.dsl.module
import presentation.*
import presentation.easyMeal.EasyMealView


val presentationModule = module {
    factory { ViewUtil() }
    factory { GetIraqiMealsView(get(), get()) }
    factory { MealGuessGameView(get()) }
    factory { EasyMealView(get()) }
    factory { SuggestSweetWithoutEggView(get(), get()) }

    factoryOf(::FoodChangeModeConsoleUI)
}
