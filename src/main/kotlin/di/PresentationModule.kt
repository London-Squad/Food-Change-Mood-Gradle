package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.*
import presentation.easyMeal.EasyMealView
import presentation.foodCulture.CountryFoodCultureView


val presentationModule = module {
    factory { ViewUtil() }
    factory { IraqiMealsView(get(), get()) }
    factory { SuggestSweetWithoutEggView(get(), get()) }
    factory { MealGuessGameView(get()) }
    factory { EasyMealView(get()) }
    factory { ItalianFoodForLargeGroupView(get(), get()) }
    factory { CountryFoodCultureView(get()) }
    factory { IngredientGameView(get()) }
    factory { KetoSuggetionHelperView(get(), get()) }

    factoryOf(::FoodChangeModeConsoleUI)
}
