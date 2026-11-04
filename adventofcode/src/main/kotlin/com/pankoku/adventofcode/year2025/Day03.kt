package com.pankoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pankoku.adventofcode.solver.DaySolver
import com.pankoku.adventofcode.utils.green

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
class Day03: DaySolver {
    override val day = 3
    override val year = 2025

    override fun solvePart1(input: String): String {
        val lines = input.lines().filter { it.isNotBlank() }
        val ans = largestJoltage(lines)
        return "\nProcessed ${lines.size} lines for Part 1. Answer is $ans".green()
    }

    fun largestJoltage(lines: List<String>): Int {
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
            println("the total output joltage is $sum")
        }
        return sum
    }
}