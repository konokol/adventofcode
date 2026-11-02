package com.pankoku.adventofcode.client

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class AocInputFetcher(
    var year: Int = 2025,
    private val sessionProvider: SessionProvider = SessionProvider,
    private val cacheManager: InputCacheManager = InputCacheManager(),
    private val httpClient: OkHttpClient = OkHttpClient()
) {

    /**
     * Gets the puzzle input for the specified day as a raw String.
     * Uses cached input if available, otherwise downloads from adventofcode.com.
     */
    fun getInput(day: Int, targetYear: Int = year): String {
        require(day in 1..25) { "Day must be between 1 and 25, given: $day" }

        if (cacheManager.hasCache(targetYear, day)) {
            return cacheManager.readCache(targetYear, day)
        }

        val downloadedInput = fetchFromNetwork(day, targetYear)
        cacheManager.saveCache(targetYear, day, downloadedInput)
        return downloadedInput
    }

    /**
     * Gets the puzzle input for the specified day split by lines.
     */
    fun getInputLines(day: Int, targetYear: Int = year): List<String> {
        return getInput(day, targetYear).lines()
    }

    private fun fetchFromNetwork(day: Int, targetYear: Int): String {
        val sessionToken = sessionProvider.getSessionToken()
        val url = "https://adventofcode.com/$targetYear/day/$day/input"

        val request = Request.Builder()
            .url(url)
            .header("Cookie", "session=$sessionToken")
            .header("User-Agent", "AdventOfCode-Kotlin-Fetcher/1.0 (+via github.com/pankoku/adventofcode)")
            .build()

        val response = try {
            httpClient.newCall(request).execute()
        } catch (e: IOException) {
            throw IOException("Network error while downloading input for year $targetYear day $day: ${e.message}", e)
        }

        response.use { resp ->
            val bodyString = resp.body.string()

            when (resp.code) {
                200 -> return bodyString
                400, 403 -> throw IllegalStateException(
                    "Authentication failed (HTTP ${resp.code}). Your Advent of Code session token may be invalid or expired."
                )
                404 -> throw IllegalStateException(
                    "Puzzle input not found (HTTP 404). Day $day for year $targetYear may not be unlocked yet."
                )
                else -> throw IllegalStateException(
                    "Failed to fetch input for year $targetYear day $day. HTTP ${resp.code}: ${resp.message}\n$bodyString"
                )
            }
        }
    }
}
