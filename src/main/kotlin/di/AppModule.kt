package di

import data.FakeMealsDataSource
import data.FileReader
import data.csvData.CsvMealsParser
import data.search.*
import logic.*
import org.koin.dsl.module
import java.io.File

val appModule = module {
    single { File("food.csv") }
    single { FileReader(get()) }
    single { CsvMealsParser() }
}