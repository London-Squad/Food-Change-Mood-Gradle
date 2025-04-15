package data

import java.io.File

class FileLoader (
    private val file : File
) {
    fun getLines() : List<String> {
        return file.readLines()
    }
}