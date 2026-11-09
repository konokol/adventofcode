package com.pancoku.adventofcode.client

import java.io.File

class InputCacheManager(
    private val baseDir: File = resolveInputsDirectory()
) {

    fun getInputFile(year: Int, day: Int): File {
        val paddedDay = String.format("%02d", day)
        return File(baseDir, "$year/day$paddedDay.txt")
    }

    fun hasCache(year: Int, day: Int): Boolean {
        val file = getInputFile(year, day)
        return file.exists() && file.isFile && file.length() > 0
    }

    fun readCache(year: Int, day: Int): String {
        val file = getInputFile(year, day)
        check(file.exists()) { "Cache file does not exist: ${file.absolutePath}" }
        return file.readText()
    }

    fun saveCache(year: Int, day: Int, content: String): File {
        val file = getInputFile(year, day)
        file.parentFile?.mkdirs()
        file.writeText(content)
        return file
    }

    companion object {
        private fun resolveInputsDirectory(): File {
            val userDir = File("./")
            val inputsDir = if (userDir.name == "adventofcode") {
                File(userDir.parentFile, ".inputs")
            } else {
                File(userDir, ".inputs")
            }
            inputsDir.mkdirs()
            return inputsDir
        }
    }
}
