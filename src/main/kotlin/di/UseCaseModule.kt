package di

//import data.FakeMealsDataSource
import data.csvData.CsvMealsDataSourceOneTimeLoad
import logic.*
import logic.search.InMemorySearchCache
import logic.search.InvertedIndexBuilder
import logic.search.LevenshteinSearch
import logic.search.MealSearchRepositoryImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<MealsDataSource> { CsvMealsDataSourceOneTimeLoad(get(), get(), 50000) }

    single<IndexBuilder> { InvertedIndexBuilder() }
    single<SearchCache> { InMemorySearchCache() }
    single<TextSearchAlgorithm> { LevenshteinSearch() }
    single<MealSearchRepository> { MealSearchRepositoryImpl(get(), get(), get(), get()) }

    single { GetIraqiMealsUseCase(get()) }
    single { GuessMealGameUseCase(get()) }
    single { GetMealsContainPotatoUseCase(get()) }
}