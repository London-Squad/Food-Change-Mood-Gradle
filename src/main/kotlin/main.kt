import data.FakeMealsDataSource
import presentation.FoodChangeModeConsoleUI

fun main() {

    //TODO: after create new feature, edit the UI class to present it then pass its useCase to the UI
    val mealsDataSource = FakeMealsDataSource()

    val ui= FoodChangeModeConsoleUI()
    ui.start()
}