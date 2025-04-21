package presentation.utils

class UserInputReaderImpl: UserInputReader {
    override fun getUserInput(message: String): String {
        print(message)
        return readln()
    }

    override fun getValidUserInput(
        validatingLambda: (String)->Boolean,
        message: String,
        invalidInputMessage: String
    ): String {
        print(message)
        val userInput = readln().trim()
        if (validatingLambda(userInput)) return userInput
        println(invalidInputMessage)
        return getValidUserInput(validatingLambda, message, invalidInputMessage)
    }
}