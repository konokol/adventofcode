package com.pankoku.adventofcode.client

import kotlin.test.Test
import kotlin.test.assertFailsWith

class SessionProviderTest {

    @Test
    fun testMissingSessionThrowsExceptionWhenNoFileOrEnv() {
        // If neither session.txt exists nor AOC_SESSION env variable is set, it should throw IllegalStateException
        val envSession = System.getenv("AOC_SESSION")
        val sessionFileExists = java.io.File("session.txt").exists() || java.io.File("../session.txt").exists()

        if (envSession.isNullOrBlank() && !sessionFileExists) {
            assertFailsWith<IllegalStateException> {
                SessionProvider.getSessionToken()
            }
        }
    }
}
