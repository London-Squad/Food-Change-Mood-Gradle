package di

import org.koin.core.qualifier.named
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.*

import presentation.easyMeal.EasyMealView
import presentation.foodCulture.CountryFoodCultureView
import presentation.healthyFastFoodMeals.GetHealthyFastFoodMealsView
import presentation.iraqiMeals.GetIraqiMealsView
import presentation.gymHelper.GymHelperView
import presentation.ingredientGame.IngredientGameView
import presentation.italianFoodForLargeGroup.ItalianFoodForLargeGroupView
import presentation.ketoSuggestionHelper.KetoSuggestionHelperView
import presentation.mealGuessGame.MealGuessGameView
import presentation.mealSearchByDate.MealSearchByDateView
import presentation.mealSearchByName.MealSearchByNameView
import presentation.suggestSweetWithoutEgg.SuggestSweetWithoutEggView
import presentation.utils.ViewUtil


val presentationModule = module {
    factory { MealSearchByNameView(get(named("byName")), get()) }
    factory { MealSearchByDateView(get(named("byDate")), get()) }

    factory { ViewUtil() }
    factory { GetIraqiMealsView(get(), get()) }
    factory { SuggestSweetWithoutEggView(get(), get()) }
    factory { MealGuessGameView(get()) }
    factory { EasyMealView(get()) }
    factory { ItalianFoodForLargeGroupView(get(), get()) }
    factory { CountryFoodCultureView(get()) }
    factory { IngredientGameView(get()) }
    factory { GymHelperView(get(), get()) }
    factory { GetHealthyFastFoodMealsView(get()) }
    factory { KetoSuggestionHelperView(get(), get()) }

    factoryOf(::FoodChangeModeConsoleUI)
}