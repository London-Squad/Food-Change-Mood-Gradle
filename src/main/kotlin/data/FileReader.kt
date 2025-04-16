package data

import java.io.File

class FileReader (
    private val file : File
) {
    fun getText() : String {
        return file.readText()
    }
}