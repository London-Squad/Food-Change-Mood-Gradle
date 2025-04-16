package di

import data.csvData.CsvMealsDataSource
import logic.*
import logic.search.InMemorySearchCache
import logic.search.InvertedIndexBuilder
import logic.search.LevenshteinSearch
import logic.search.MealSearchRepositoryImpl
import org.koin.dsl.module


val useCaseModule = module {
    single<MealsDataSource> { CsvMealsDataSource(get(), get()) }
    single<IndexBuilder> { InvertedIndexBuilder() }
    single<SearchCache> { InMemorySearchCache() }
    single<TextSearchAlgorithm> { LevenshteinSearch() }
    single<MealSearchRepository> { MealSearchRepositoryImpl(get<MealsDataSource>(), get(), get(), get()) }
    single { GetIraqiMealsUseCase(get()) }

}