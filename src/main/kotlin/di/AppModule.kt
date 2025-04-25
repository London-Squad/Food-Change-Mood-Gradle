package di

import data.FileReader
import data.csvData.CsvMealsDataSourceOneTimeLoad
import data.csvData.CsvMealsParser
import logic.MealsDataSource
import org.koin.dsl.module
import java.io.File

val appModule = module {
    single { File("food.csv") }
    single { FileReader(get()) }
    single { CsvMealsParser() }
    single<MealsDataSource> { CsvMealsDataSourceOneTimeLoad(get(), get(), 500) }
}