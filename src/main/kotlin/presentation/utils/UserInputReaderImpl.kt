package presentation.utils

class UserInputReaderImpl(
    private val cliPrinter: CLIPrinter
): UserInputReader {
    override fun getUserInput(message: String): String {
        cliPrinter.cliPrint(message)
        return readln()
    }

    override fun getValidUserInput(
        isValidInput: (String)->Boolean,
        message: String,
        invalidInputMessage: String
    ): String {
        val userInput = getUserInput(message).trim()
        if (isValidInput(userInput)) return userInput
        cliPrinter.cliPrintLn(invalidInputMessage)
        return getValidUserInput(isValidInput, message, invalidInputMessage)
    }
}