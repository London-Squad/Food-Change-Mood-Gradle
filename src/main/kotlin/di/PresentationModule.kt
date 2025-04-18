package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.*
import presentation.easyMeal.EasyMealView


val presentationModule = module {
    single { ViewUtil() }
    single { SuggestSweetWithoutEggView(get(), get()) }
    single { GetIraqiMealsView(get(), get()) }
    single { MealGuessGameView(get()) }
    single { EasyMealView(get()) }

    single { FoodChangeModeConsoleUI(
        get(),
        get(),
        get(),
        get()
        ) }
}
