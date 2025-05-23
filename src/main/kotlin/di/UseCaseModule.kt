package di

import logic.*
import logic.ketoMealHelper.GetKetoMealUseCase
import logic.getIraqiMeals.GetIraqiMealsUseCase
import logic.search.byDate.IdIndexBuilder
import logic.search.byDate.MealDateInvertedIndexBuilder
import logic.search.byDate.MealSearchByDateUseCaseImpl
import logic.search.byName.InMemorySearchCache
import logic.search.byName.MealNameInvertedIndexBuilder
import logic.search.byName.MealSearchByNameUseCaseImpl
import model.Meal
import org.koin.core.qualifier.named
import logic.easyMealsSuggestion.EasyMealsSuggestionUseCase
import logic.exploreCountryFoodCulture.ExploreCountryFoodCultureUseCase
import logic.getHealthyFastFoodMeals.GetHealthyFastFoodMealsUseCase
import logic.getHighCalorieMeals.GetHighCalorieMealsUseCase
import logic.getItalianFoodForLargeGroup.GetItalianFoodForLargeGroupUseCase
import logic.getMealsContainPotato.GetMealsContainPotatoUseCase
import logic.getSeaFoodMealsUseCase.GetSeaFoodMealsUseCase
import logic.gymHelper.GymHelperUseCase
import logic.ingredientGame.IngredientGameUseCase
import logic.mealGuessGame.MealGuessGameUseCase
import logic.search.*
import logic.suggestSweetWithoutEgg.GetSweetWithoutEggUseCase
import org.koin.dsl.module
import java.time.LocalDate

val useCaseModule = module {

    // Index Builders
    single { MealNameInvertedIndexBuilder(get<MealsDataSource>()) }
    single { MealDateInvertedIndexBuilder(get<MealsDataSource>()) }
    single{ IdIndexBuilder(get<MealsDataSource>()) }

    // Search Cache and Algorithm
    single<SearchCache> { InMemorySearchCache() }
    single<TextSearchAlgorithm> { LevenshteinSearch() }

    // Use Cases
    single<MealSearchUseCase<List<Meal>>>(named("byName")) {
        MealSearchByNameUseCaseImpl(
            mealsDataSource = get<MealsDataSource>(),
            searchAlgorithm = get<TextSearchAlgorithm>(),
            cache = get<SearchCache>(),
            indexBuilder = get()
        )
    }

    single<MealSearchUseCase<List<Pair<Int, String>>>>(named("byDate")) {
        MealSearchByDateUseCaseImpl(
            mealsDataSource = get<MealsDataSource>(),
            dateIndexBuilder = get(),
            idIndexBuilder = get()
        )
    }

    single { GetIraqiMealsUseCase(get()) }
    single { MealGuessGameUseCase(get()) }
    single { GetKetoMealUseCase(get()) }
    single { GetHealthyFastFoodMealsUseCase(get()) }
    single { IngredientGameUseCase(get()) }
    single { EasyMealsSuggestionUseCase(get()) }
    single { ExploreCountryFoodCultureUseCase(get()) }
    single { GetSweetWithoutEggUseCase(get()) }
    single { GetItalianFoodForLargeGroupUseCase(get()) }
    single { GymHelperUseCase(get()) }
    single { GetHighCalorieMealsUseCase(get()) }
    single { GetMealsContainPotatoUseCase(get()) }
    single { GetSeaFoodMealsUseCase(get()) }
}