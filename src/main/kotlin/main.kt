import data.FileLoader
import java.io.File

fun main () {
    val csvFile = File("food.csv")
    val fileLoader = FileLoader(csvFile)

}