package di

//import data.FakeMealsDataSource
import data.csvData.CsvMealsDataSourceOneTimeLoad
import logic.*
import logic.search.InMemorySearchCache
import logic.search.InvertedIndexBuilder
import logic.search.LevenshteinSearch
import logic.search.MealSearchRepositoryImpl
import logic.useCase.EasyMealsSuggestionUseCase
import logic.useCase.ExploreCountryFoodCultureUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<MealsDataSource> { CsvMealsDataSourceOneTimeLoad(get(), get(), 50000) }

    single<IndexBuilder> { InvertedIndexBuilder() }
    single<SearchCache> { InMemorySearchCache() }
    single<TextSearchAlgorithm> { LevenshteinSearch() }
    single<MealSearchRepository> { MealSearchRepositoryImpl(get(), get(), get(), get()) }
    single { GetIraqiMealsUseCase(get()) }
    single { MealGuessGameUseCase(get()) }
    single { KetoFriendlyValidator() }
    single { KetoMealHelper(get(), get()) }
    single { GetHealthyFastFoodMealsUseCase(get()) }
    single { IngredientGameUseCase(get()) }
    single { EasyMealsSuggestionUseCase(get()) }
    single { ExploreCountryFoodCultureUseCase(get()) }
    single { SuggestSweetWithoutEggUseCase(get()) }
    single { GetItalianFoodForLargeGroupUseCase(get()) }
    single { GymHelperUseCase(get()) }
    single { GetSeaFoodMealsUseCase(get()) }
}