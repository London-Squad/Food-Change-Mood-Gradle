package di

import data.FakeMealsDataSource
import data.search.*
import logic.*
import org.koin.dsl.module


val useCaseModule = module {
    single<MealsDataSource> { FakeMealsDataSource() }

    single<IndexBuilder> { InvertedIndexBuilder() }
    single<SearchCache> { InMemorySearchCache() }
    single<TextSearchAlgorithm> { LevenshteinSearch() }
    single { MealSearchService(get()) }
    single<MealSearchRepository> { MealSearchRepositoryImpl(get<MealsDataSource>(), get(), get(), get()) }

}