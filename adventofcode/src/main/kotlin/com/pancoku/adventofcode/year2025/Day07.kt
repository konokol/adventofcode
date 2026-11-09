package com.pancoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pancoku.adventofcode.solver.DaySolver
import com.pancoku.adventofcode.utils.green

/**
 * Copyright ©2026 [Pancoku]. All rights reserved.
 * Day07
 * <p>
 * @author lijun
 * @since 2026-11-09 08:01
 * @version 1.0
 * 
 */
@AutoService(DaySolver::class)
class Day07 : DaySolver {

    override val day = 7
    override val year = 2025

    private val DEBUG = false

    override fun solvePart1(input: String): String {
        val lines = input.lines()
            .filter { it.isNotBlank() }
            .map { it.toCharArray() }
        val answer = splitBeam(lines).toString().green()
        return "\nProcessed ${lines.size} lines. The beam be split $answer times."
    }

    private fun splitBeam(lines: List<CharArray>): Int {
        print(lines)
        var sum = 0
        for (r in 0 until lines.size - 1) {
            for (c in lines[r].indices) {
                if (lines[r][c] == 'S') {
                    lines[r + 1][c] = '|'
                } else if (lines[r][c] == '|') {
                    if (r + 1 in lines.indices) {
                        if (lines[r + 1][c] == '.') {
                            // go through
                            lines[r + 1][c] = '|'
                        } else if (lines[r + 1][c] == '^') {
                            // split
                            sum++
                            if (c - 1 in lines[r + 1].indices) {
                                lines[r + 1][c - 1] = '|'
                            }
                            if (c + 1 in lines[r + 1].indices) {
                                lines[r + 1][c + 1] = '|'
                            }
                        }
                    }
                }
            }
            print(lines)
        }
        return sum
    }

    private fun print(lines: List<CharArray>) {
        if (DEBUG) {
            println(lines.joinToString("\n") { charArray -> String(charArray) })
            Thread.sleep(1000)
        }
    }

    override fun solvePart2(input: String): String {
        val lines = input.lines()
            .filter { it.isNotBlank() }
            .map { it.toCharArray() }
        val answer = countOfTimelines(lines).toString().green()
        return "\nProcessed ${lines.size} lines. The beam be split $answer times."
    }

    private fun countOfTimelines(lines: List<CharArray>): Long {
        // dp[row][col] means how many tachyon reached here
        val dp = Array(lines.size) { LongArray(lines[0].size) { 0 } }
        val start = lines.first().indexOf('S')
        dp[0][start] = 1

        for (r in 0 until lines.size - 1) {
            for (c in lines[0].indices) {
                if (dp[r][c] == 0L) continue
                if (lines[r + 1][c] == '.') {
                    // go through
                    dp[r + 1][c] += dp[r][c]
                } else if (lines[r + 1][c] == '^') {
                    // split
                    if (c - 1 in lines[0].indices) {
                        dp[r + 1][c - 1] += dp[r][c]
                    }
                    if (c + 1 in lines[0].indices) {
                        dp[r + 1][c + 1] += dp[r][c]
                    }
                }
            }
        }
        return dp.last().sum()
    }
}
