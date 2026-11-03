package com.pankoku.adventofcode.client

import java.io.File

object SessionProvider {

    /**
     * Resolves the Advent of Code session cookie.
     * Checks `session.txt` at the root directory first, then falls back to `AOC_SESSION` environment variable.
     */
    fun getSessionToken(): String {
        val sessionFile = findSessionFile()
        if (sessionFile != null && sessionFile.exists()) {
            val rawToken = sessionFile.readText().trim()
            if (rawToken.isNotBlank()) {
                return rawToken
            }
        }

        val envToken = System.getenv("AOC_SESSION")?.trim()
        if (!envToken.isNullOrBlank()) {
            return envToken
        }

        throw IllegalStateException(
            """
            Advent of Code session token not found!
            Please either:
              1. Create a `session.txt` file in the project root directory containing your session cookie.
              2. Set the `AOC_SESSION` environment variable.
            """.trimIndent()
        )
    }

    private fun findSessionFile(): File? {
        val candidates = listOf(
            File("session.txt"),
            File("../session.txt"),
            File(System.getProperty("user.dir"), "session.txt")
        )
        return candidates.firstOrNull { it.exists() && it.isFile }
    }
}
