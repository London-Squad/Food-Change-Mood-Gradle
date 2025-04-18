package presentation

import logic.SweetWithoutEggSuggesterUseCase

class SweetWithoutEggSuggesterView(
    private val sweetWithoutEggSuggesterUseCase: SweetWithoutEggSuggesterUseCase,
    private val viewUtil: ViewUtil
) : BaseView {

    override fun start() {
        printHeader()

        sweetWithoutEggSuggesterUseCase.clearSuggestedList()
        getSweetWithoutEggs()
    }

    private fun printHeader() {
        println("------------------------------------------")
        println("           Sweet Without Egg              ")
        println("------------------------------------------")
    }

    private fun getSweetWithoutEggs() {

        val sweet = sweetWithoutEggSuggesterUseCase.suggestSweet()

        if (sweet == null) {
            println("No more sweets to suggest!")
            return
        }

        println("\nTry this sweet: ${sweet.name}")
        println("Description: ${sweet.description}")

        println("Do you like it? (y = like, n = dislike, x = exit): ")
        do{
            when (readlnOrNull()?.lowercase()) {
                "y" -> {
                    viewUtil.printMeal(sweet)
                    return
                }
                "n" -> {
                    getSweetWithoutEggs()
                    return
                }
                "x" -> {
                    println("Returning to main menu...")
                    return
                }
                else -> println("Invalid input. Try again.")
            }
        }while (true)
    }
}