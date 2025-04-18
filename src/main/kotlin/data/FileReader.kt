package data

import java.io.File
import java.io.FileNotFoundException

class FileReader (
    private val file : File
) {
    fun getText() : String {
        return try {
            file.readText()
        } catch (e: FileNotFoundException){
            ""
        }
    }
}