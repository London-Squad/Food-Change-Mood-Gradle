package di

//import data.FakeMealsDataSource
import data.csvData.CsvMealsDataSourceOneTimeLoad
import logic.*
import logic.search.InMemorySearchCache
import logic.search.InvertedIndexBuilder
import logic.search.LevenshteinSearch
import logic.search.MealSearchRepositoryImpl
import org.koin.dsl.module
import presentation.FoodChangeModeConsoleUI
import presentation.GetIraqiMealsView
import presentation.ViewUtil


val useCaseModule = module {
    // data source
    single<MealsDataSource> { CsvMealsDataSourceOneTimeLoad(get(), get(), 50000) }

    // search
    single<IndexBuilder> { InvertedIndexBuilder() }
    single<SearchCache> { InMemorySearchCache() }
    single<TextSearchAlgorithm> { LevenshteinSearch() }
    single<MealSearchRepository> { MealSearchRepositoryImpl(get(), get(), get(), get()) }

    single { ViewUtil() }

    single{GetIraqiMealsUseCase(get())}
    single{GetIraqiMealsView(get(), get())}

    // ui

    single {
        FoodChangeModeConsoleUI(
            get()
        )
    }

}