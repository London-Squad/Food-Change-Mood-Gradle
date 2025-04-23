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
import presentation.highCalorieMeals.HighCalorieMealsView
import presentation.ingredientGame.IngredientGameView
import presentation.iraqiMeals.IraqiMealsView
import presentation.italianFoodForLargeGroup.ItalianFoodForLargeGroupView
import presentation.ketoSuggestionHelper.KetoSuggestionHelperView
import presentation.mealGuessGame.MealGuessGameView
import presentation.mealSearchByDate.MealSearchByDateView
import presentation.mealSearchByName.MealSearchByNameView
import presentation.mealsContainPotato.GetMealsContainPotatoView
import presentation.suggestSweetWithoutEgg.SweetWithoutEggView
import presentation.utils.*


val presentationModule = module {
    factory { MealSearchByNameView(get(named("byName")), get(), get(), get()) }

    factory { UIMealsListPrinterAndSelectByID(get(), get(), get()) }
    factory { MealSearchByDateView(get(named("byDate")), get(), get(), get(), get()) }

    factory { IraqiMealsView(get(), get()) }
    factory { SweetWithoutEggView(get(), get(), get(), get()) }
    factory { MealGuessGameView(get(), get(), get(), get()) }
    factory { EasyMealView(get(), get()) }
    factory { ItalianFoodForLargeGroupView(get(), get()) }
    factory { CountryFoodCultureView(get(), get(), get(), get()) }
    factory { IngredientGameView(get(), get(), get(), get()) }
    factory { GymHelperView(get(), get(), get(), get()) }
    factory { GetHealthyFastFoodMealsView(get(), get(), get()) }
    factory { KetoSuggestionHelperView(get(), get(), get(), get()) }
    factory { GetSeaFoodMealsView(get(), get()) }
    factory { GetMealsContainPotatoView(get(), get()) }
    factory { HighCalorieMealsView(get(), get(), get(), get()) }

    factory<UserInputReader> { UserInputReaderImpl(get()) }
    factory { CLIPrinter() }
    factory { UIMealPrinter(get()) }
    factory { UIMealsListPrinter(get(), get(), get()) }



    factoryOf(::FoodChangeModeConsoleUI)
}