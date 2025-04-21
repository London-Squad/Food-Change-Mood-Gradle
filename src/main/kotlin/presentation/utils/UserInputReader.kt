package presentation.utils

interface UserInputReader {
    fun getUserInput(message: String = ""): String

    fun getValidUserInput(
        validatingLambda: (String)->Boolean = { true },
        message: String = "Your Input: ",
        invalidInputMessage: String = "Invalid Input"
    ): String
}