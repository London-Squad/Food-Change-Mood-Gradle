package di

import org.koin.core.qualifier.named
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.*

import presentation.easyMeal.EasyMealView
import presentation.getSeaFoodMealsView.GetSeaFoodMealsView
import presentation.foodCulture.CountryFoodCultureView
import presentation.healthyFastFoodMeals.GetHealthyFastFoodMealsView
import presentation.gymHelper.GymHelperView
import presentation.highCalorieMeals.GetHighCalorieMealsView
import presentation.ingredientGame.IngredientGameView
import presentation.iraqiMeals.IraqiMealsView
import presentation.italianFoodForLargeGroup.ItalianFoodForLargeGroupView
import presentation.ketoSuggestionHelper.KetoSuggestionHelperView
import presentation.mealGuessGame.MealGuessGameView
import presentation.mealSearchByDate.MealSearchByDateView
import presentation.mealSearchByName.MealSearchByNameView
import presentation.mealsContainPotato.GetMealsContainPotatoView
import presentation.suggestSweetWithoutEgg.SuggestSweetWithoutEggView
import presentation.utils.*


val presentationModule = module {
    factory { MealSearchByNameView(get(named("byName")), get(), get(), get()) }
    factory { MealSearchByDateView(get(named("byDate")), get(), get(), get()) }

    factory { IraqiMealsView(get(), get()) }
    factory { SuggestSweetWithoutEggView(get(), get(), get(), get()) }
    factory { MealGuessGameView(get(), get(), get(), get()) }
    factory { EasyMealView(get(), get()) }
    factory { ItalianFoodForLargeGroupView(get(), get()) }
    factory { CountryFoodCultureView(get(), get(), get(), get()) }
    factory { IngredientGameView(get(), get(), get(), get()) }
    factory { GymHelperView(get(), get(), get(), get()) }
    factory { GetHealthyFastFoodMealsView(get(), get(), get()) }
    factory { KetoSuggestionHelperView(get(), get(), get()) }
    factory { GetSeaFoodMealsView(get(), get()) }
    factory { GetMealsContainPotatoView(get(), get()) }
    factory { ItalianFoodForLargeGroupView(get(), get(), get()) }
    factory { CountryFoodCultureView(get(), get()) }
    factory { IngredientGameView(get(), get()) }
    factory { GymHelperView(get(), get(), get()) }
    factory { GetHealthyFastFoodMealsView(get()) }
    factory { KetoSuggestionHelperView(get(), get(), get()) }
    factory { GetSeaFoodMealsView(get(), get(), get()) }
    factory { GetMealsContainPotatoView(get(), get(), get()) }
    factory { GetHighCalorieMealsView(get(), get(), get()) }

    factory <UserInputReader>{ UserInputReaderImpl(get()) }
    factory { CLIPrinter() }
    factory { UIMealPrinter(get()) }
    factory { UIMealsListPrinter(get(), get(), get()) }



    factoryOf(::FoodChangeModeConsoleUI)
}