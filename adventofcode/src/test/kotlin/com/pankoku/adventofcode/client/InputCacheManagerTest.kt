package com.pankoku.adventofcode.client

import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InputCacheManagerTest {

    @Test
    fun testCacheSaveAndRead() {
        val tempDir = Files.createTempDirectory("aoc_test_cache").toFile()
        try {
            val cacheManager = InputCacheManager(tempDir)

            assertFalse(cacheManager.hasCache(2025, 1))

            val sampleContent = "123\n456\n789"
            val savedFile = cacheManager.saveCache(2025, 1, sampleContent)

            assertTrue(savedFile.exists())
            assertTrue(cacheManager.hasCache(2025, 1))
            assertEquals(sampleContent, cacheManager.readCache(2025, 1))
            assertEquals("day01.txt", savedFile.name)
        } finally {
            tempDir.deleteRecursively()
        }
    }
}
