package di

//import data.FakeMealsDataSource
import data.csvData.CsvMealsDataSourceOneTimeLoad
import logic.*
import logic.search.LevenshteinSearch
import logic.search.byDate.MealDateInvertedIndexBuilder
import logic.search.byDate.MealSearchByDateUseCaseImpl
import logic.search.byName.InMemorySearchCache
import logic.search.byName.MealNameInvertedIndexBuilder
import logic.search.byName.MealSearchByNameUseCaseImpl
import model.Meal
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.time.LocalDate

val useCaseModule = module {
    single<MealsDataSource> { CsvMealsDataSourceOneTimeLoad(get(), get(), 50000) }

    //
    single<IndexBuilder<String, Set<Int>>> { MealNameInvertedIndexBuilder() }

    single<IndexBuilder<LocalDate, List<Int>>> { MealDateInvertedIndexBuilder() }


    //
    single<SearchCache> { InMemorySearchCache() }

    single<TextSearchAlgorithm> { LevenshteinSearch() }

    //
    single<MealSearchUseCase<List<Meal>>>(named("byName")) { MealSearchByNameUseCaseImpl(get<MealsDataSource>(), get(), get(), get()) }

    single<MealSearchUseCase<List<Pair<Int, String>>>> (named("byDate")){ MealSearchByDateUseCaseImpl(get(),get()) }

    single { GetIraqiMealsUseCase(get()) }
    single { MealGuessGameUseCase(get()) }
}