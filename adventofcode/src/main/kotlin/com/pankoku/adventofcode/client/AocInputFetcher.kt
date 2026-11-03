package com.pankoku.adventofcode.client

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class AocInputFetcher(
    var year: Int = 2026,
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
        val rawToken = sessionProvider.getSessionToken()
        val cookieHeader = if (rawToken.startsWith("session=") || rawToken.contains("=")) {
            rawToken
        } else {
            "session=$rawToken"
        }

        val url = "https://adventofcode.com/$targetYear/day/$day/input"

        val request = Request.Builder()
            .url(url)
            .header("Cookie", cookieHeader)
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
            .build()

        val response = try {
            httpClient.newCall(request).execute()
        } catch (e: IOException) {
            throw IOException("Network error while downloading input for year $targetYear day $day: ${e.message}", e)
        }

        response.use { resp ->
            val bodyString = resp.body.string()
            val wafAction = resp.header("x-amzn-waf-action")

            if (resp.isSuccessful && bodyString.isNotBlank() && wafAction == null) {
                return bodyString
            }

            if (resp.code == 202 || wafAction == "challenge" || bodyString.isBlank()) {
                val paddedDay = String.format("%02d", day)
                throw IllegalStateException(
                    """
                    AWS WAF Bot Challenge triggered (HTTP 202 / empty response).

                    How to fix:
                      Option 1 (Network Fetching):
                        Open DevTools (F12) on https://adventofcode.com in your browser,
                        copy the full 'Cookie' header (including 'aws-waf-token=...'),
                        and paste it into `session.txt`.

                      Option 2 (Manual Input):
                        Open https://adventofcode.com/$targetYear/day/$day/input in your browser,
                        and save the input text directly to:
                        .inputs/$targetYear/day$paddedDay.txt
                    """.trimIndent()
                )
            }

            when (resp.code) {
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
