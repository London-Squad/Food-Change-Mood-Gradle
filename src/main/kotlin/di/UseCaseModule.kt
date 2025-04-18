package di

import data.csvData.CsvMealsDataSourceOneTimeLoad
import logic.*
import logic.search.LevenshteinSearch
import logic.search.byDate.IdIndexBuilder
import logic.search.byDate.MealDateInvertedIndexBuilder
import logic.search.byDate.MealSearchByDateUseCaseImpl
import logic.search.byName.InMemorySearchCache
import logic.search.byName.MealNameInvertedIndexBuilder
import logic.search.byName.MealSearchByNameUseCaseImpl
import model.Meal
import org.koin.core.qualifier.named
import logic.useCase.EasyMealsSuggestionUseCase
import logic.useCase.ExploreCountryFoodCultureUseCase
import org.koin.dsl.module
import java.time.LocalDate

val useCaseModule = module {
    single<MealsDataSource> { CsvMealsDataSourceOneTimeLoad(get(), get(), 50000) }

    // Index Builders
    single<IndexBuilder<String, Set<Int>>>(named("NAME")) { MealNameInvertedIndexBuilder(get<MealsDataSource>()) }
    single<IndexBuilder<LocalDate, List<Int>>>(named("DATE")) { MealDateInvertedIndexBuilder(get<MealsDataSource>()) }
    single<IndexBuilder<Int, Int>>(named("ID")) { IdIndexBuilder(get<MealsDataSource>()) }

    // Search Cache and Algorithm
    single<SearchCache> { InMemorySearchCache() }
    single<TextSearchAlgorithm> { LevenshteinSearch() }

    // Use Cases
    single<MealSearchUseCase<List<Meal>>>(named("byName")) {
        MealSearchByNameUseCaseImpl(
            mealsDataSource = get<MealsDataSource>(),
            searchAlgorithm = get<TextSearchAlgorithm>(),
            cache = get<SearchCache>(),
            indexBuilder = get<IndexBuilder<String, Set<Int>>>(named("NAME"))
        )
    }

    single<MealSearchUseCase<List<Pair<Int, String>>>>(named("byDate")) {
        MealSearchByDateUseCaseImpl(
            mealsDataSource = get<MealsDataSource>(),
            dateIndexBuilder = get<IndexBuilder<LocalDate, List<Int>>>(named("DATE")),
            idIndexBuilder = get<IndexBuilder<Int, Int>>(named("ID"))
        )
    }

    single { GetIraqiMealsUseCase(get()) }
    single { MealGuessGameUseCase(get()) }
    single { IngredientGameUseCase(get()) }
    single { EasyMealsSuggestionUseCase(get()) }
    single { ExploreCountryFoodCultureUseCase(get()) }
    single { SuggestSweetWithoutEggUseCase(get()) }
}