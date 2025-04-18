package di

import logic.GetMealsContainPotatoUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.*
import presentation.easyMeal.EasyMealView


val presentationModule = module {
    factory { ViewUtil() }
    factory { SuggestSweetWithoutEggView(get(), get()) }
    factory { GetIraqiMealsView(get(), get()) }
    factory { GuessMealGameView(get()) }
    factory { EasyMealView(get()) }
    single { GetMealsContainPotatoView(get(), get()) }

    factoryOf(::FoodChangeModeConsoleUI)
}