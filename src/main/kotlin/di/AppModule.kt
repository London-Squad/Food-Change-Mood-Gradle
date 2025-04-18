package di

import data.FileReader
import data.csvData.CsvMealsParser
import org.koin.dsl.module
import java.io.File

val appModule = module {
    single { File("foood.csv") }
    single { FileReader(get()) }
    single { CsvMealsParser() }
}