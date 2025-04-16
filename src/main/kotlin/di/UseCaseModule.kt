package di

import data.FakeMealsDataSource
import logic.*
import logic.search.InMemorySearchCache
import logic.search.InvertedIndexBuilder
import logic.search.LevenshteinSearch
import logic.search.MealSearchRepositoryImpl
import org.koin.dsl.module


val useCaseModule = module {
    single<MealsDataSource> { FakeMealsDataSource() }

    single<IndexBuilder> { InvertedIndexBuilder() }
    single<SearchCache> { InMemorySearchCache() }
    single<TextSearchAlgorithm> { LevenshteinSearch() }
    single<MealSearchRepository> { MealSearchRepositoryImpl(get<MealsDataSource>(), get(), get(), get()) }

}