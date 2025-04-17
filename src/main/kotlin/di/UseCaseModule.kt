package di

import data.FakeMealsDataSource
import data.csvData.CsvMealsDataSourceOneTimeLoad
import logic.*
import logic.search.InMemorySearchCache
import logic.search.InvertedIndexBuilder
import logic.search.LevenshteinSearch
import logic.search.MealSearchRepositoryImpl
import org.koin.dsl.module
import presentation.FoodChangeModeConsoleUI


val useCaseModule = module {
    single<MealsDataSource> { CsvMealsDataSourceOneTimeLoad(get(), get(), 5) }

    single<IndexBuilder> { InvertedIndexBuilder() }
    single<SearchCache> { InMemorySearchCache() }
    single<TextSearchAlgorithm> { LevenshteinSearch() }
    single<MealSearchRepository> { MealSearchRepositoryImpl(get<MealsDataSource>(), get(), get(), get()) }

    single { FoodChangeModeConsoleUI() }

}