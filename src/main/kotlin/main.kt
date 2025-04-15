import data.CsvMealsDataSource
import data.CsvMealsParser
import data.FakeMealsDataSource
import data.FileLoader
import java.io.File

fun main () {
//    val csvFile = File("food.csv")
//    val fileLoader = FileLoader(csvFile)
//    val csvMealsParser = CsvMealsParser()
//    val mealsDataSource = CsvMealsDataSource(fileLoader, csvMealsParser)
    val mealsDataSource = FakeMealsDataSource()

    println(mealsDataSource.getAllMeals())

}