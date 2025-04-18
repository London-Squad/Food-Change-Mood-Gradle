package di

import org.koin.core.qualifier.named
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.*
import presentation.*
import presentation.*

import presentation.easyMeal.EasyMealView
import presentation.foodCulture.CountryFoodCultureView


val presentationModule = module {
    factory { MealSearchByNameView(get(named("byName")), get()) }
    factory { MealSearchByDateView(get(named("byDate")), get()) }

    factory { ViewUtil() }
    factory { IraqiMealsView(get(), get()) }
    factory { SuggestSweetWithoutEggView(get(), get()) }
    factory { MealGuessGameView(get()) }
    factory { EasyMealView(get()) }
    factory { ItalianFoodForLargeGroupView(get(), get()) }
    factory { CountryFoodCultureView(get()) }
    factory { IngredientGameView(get()) }
    factory { GymHelperView(get(), get()) }
    factory { GetHealthyFastFoodMealsView(get()) }
    factory { KetoSuggestionHelperView(get(), get()) }
    single { GetMealsContainPotatoView(get(), get()) }

    factoryOf(::FoodChangeModeConsoleUI)
}