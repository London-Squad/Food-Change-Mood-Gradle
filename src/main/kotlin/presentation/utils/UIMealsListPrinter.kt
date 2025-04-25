package presentation.utils

import model.Meal

class UIMealsListPrinter(
    private val userInputReader: UserInputReader,
    private val uiMealPrinter: UIMealPrinter,
    private val cliPrinter: CLIPrinter
) {
    private fun printLn(message: String = "") = cliPrinter.cliPrintLn(message)

    fun printMeals(
        meals: List<Meal>,
        listTitle: String = "Meals List",
        mealTextInList: (Meal) -> String = { it.name },
    ) {

        val iraqiMealsChunks = meals.chunked(MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE)

        uiMealPrinter.printHeader(listTitle)

        if (iraqiMealsChunks.isEmpty()) {
            printLn("no meals found :'("); return
        }


        printChunk(iraqiMealsChunks, mealTextInList = mealTextInList)
    }

    private fun printChunk(
        chunks: List<List<Meal>>, chunkIndex: Int = 0,
        mealTextInList: (Meal) -> String
    ) {
        if (chunks.size != 1) printLn("Page ${chunkIndex + 1}/${chunks.size}")
        printMealsList(chunks[chunkIndex], chunkIndex, mealTextInList)
        printOptions(chunkIndex, chunks.size)
        figureOutNextUI(chunks, chunkIndex, mealTextInList)
    }

    private fun figureOutNextUI(
        chunks: List<List<Meal>>, chunkIndex: Int,
        mealTextInList: (Meal) -> String
    ) {
        val mealsRanks =
            (0..chunks[chunkIndex].lastIndex).map { mealIndex -> mealIndexToRank(mealIndex, chunkIndex).toString() }
        val userInput = getUserInput(chunkIndex, chunks.size, mealsRanks)
        printLn(uiMealPrinter.getThickHorizontal())
        when (userInput) {
            "next" -> printChunk(chunks, chunkIndex + 1, mealTextInList)
            "back" -> printChunk(chunks, chunkIndex - 1, mealTextInList)
            in mealsRanks -> {
                printMealAndWaitForEnter(
                    chunks[chunkIndex][mealRankToIndex(userInput.toInt(), chunkIndex)]
                )
            }

            else -> {}
        }
    }

    private fun getUserInput(chunkIndex: Int, chunksSize: Int, mealsRanks: List<String>) =
        userInputReader.getValidUserInput(
            { isValidUserInput(it, chunkIndex, chunksSize, mealsRanks) },
            "\nyour input: ",
            "Invalid input, try again ..."
        )

    private fun isValidUserInput(
        userInput: String,
        chunkIndex: Int,
        chunksSize: Int,
        mealsRanks: List<String>
    ): Boolean =
        (userInput == "next" && chunkIndex < chunksSize - 1)
                || (userInput == "back" && chunkIndex > 0)
                || userInput in mealsRanks
                || userInput == "0"


    private fun printOptions(chunkIndex: Int, chunksSize: Int) {
        if ((chunkIndex + 1) < chunksSize) {
            printLn("write 'next' to go to the next page")
        }
        if (chunkIndex > 0) {
            printLn("write 'back' to go back to the previous page")
        }
        printLn("If you want the details of one of the meal, enter its number")
        printLn("If you want to go back to the main menu, enter 0")
    }

    private fun printMealsList(
        meals: List<Meal>, pageIndex: Int,
        mealTextInList: (Meal) -> String
    ) {
        meals.forEachIndexed { mealIndex, meal ->
            uiMealPrinter.printTextWithinWidth("${mealIndexToRank(mealIndex, pageIndex)}. " + mealTextInList(meal))
        }
        printLn(uiMealPrinter.getThinHorizontal())
    }

    private fun mealIndexToRank(mealIndex: Int, pageIndex: Int) =
        mealIndex + 1 + pageIndex * MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE

    private fun mealRankToIndex(mealRank: Int, pageIndex: Int) =
        mealRank - 1 - pageIndex * MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE

    private fun printMealAndWaitForEnter(meal: Meal) {
        uiMealPrinter.printMealDetails(meal)
        userInputReader.getUserInput("press Enter to go back to main menu")
    }

    private companion object {
        const val MAX_NUMBER_OF_MEALS_TO_BE_PRINTED_AT_ONCE = 10
    }
}