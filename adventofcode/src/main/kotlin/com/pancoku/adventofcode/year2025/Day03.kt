package com.pancoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pancoku.adventofcode.solver.DaySolver
import com.pancoku.adventofcode.utils.green

/**
 * Copyright ©2026 [pancoku]. All rights reserved.
 * Day03
 * <p>
 * @author lijun
 * @since 2026-11-04 13:26
 * @version 1.0
 * 
 */
@AutoService(DaySolver::class)
class Day03 : DaySolver {
    override val day = 3
    override val year = 2025

    override fun solvePart1(input: String): String {
        val lines = input.lines().filter { it.isNotBlank() }
        val ans = largestJoltage1(lines)
        return "\nProcessed ${lines.size} lines for Part 1. Answer is $ans".green()
    }

    fun largestJoltage1(lines: List<String>): Int {
        var sum = 0
        for (line in lines) {
            var firstIndex = 0
            var first = line[firstIndex]
            for (i in 0 until line.length - 1) {
                if (line[i] > first) {
                   first = line[i]
                   firstIndex =  i
                }
            }
            var second = '0'
            for (i in firstIndex + 1 until line.length) {
                if (line[i] > second) {
                    second = line[i]
                }
            }
            val value = "$first$second".toInt()
            println("In $line, the largest joltage you can produce is $value")
            sum += value
        }
        println("the total output joltage is $sum")
        return sum
    }

    override fun solvePart2(input: String): String {
        val lines = input.lines().filter { it.isNotBlank() }
        val ans = largestJoltage2(lines)
        return "\nProcessed ${lines.size} lines for Part 2. Answer is $ans".green()
    }

    fun largestJoltage2(lines: List<String>, k: Int = 12): Long {
        var sum = 0L
        for (rawLine in lines) {
            val line = rawLine.filter { it.isDigit() }
            if (line.length < k) continue

            var num = 0L
            var startIndex = 0
            val n = line.length

            for (i in 0 until k) {
                val maxAllowedIndex = n - (k - i)
                var maxChar = line[startIndex]
                var maxIndex = startIndex

                for (j in startIndex..maxAllowedIndex) {
                    if (line[j] > maxChar) {
                        maxChar = line[j]
                        maxIndex = j
                    }
                }
                num = num * 10 + (maxChar - '0')
                startIndex = maxIndex + 1
            }

            println("In $rawLine, the largest joltage you can produce is $num")
            sum += num
        }
        println("the total output joltage is $sum")
        return sum
    }
}
