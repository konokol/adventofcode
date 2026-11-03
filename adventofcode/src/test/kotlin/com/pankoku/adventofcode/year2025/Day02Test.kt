package com.pankoku.adventofcode.year2025

import kotlin.test.Test
import kotlin.test.assertTrue

class Day02Test {

    @Test
    fun testDay02Part1() {
        val day02 = Day02()
        // Ranges: 10-20 (invalid: 11), 30-40 (invalid: 33)
        // Expected sum: 11 + 33 = 44
        val result = day02.solvePart1("10-20, 30-40\n")
        assertTrue(result.contains("44"))
    }

    @Test
    fun testDay02Part2() {
        val day02 = Day02()
        // Ranges: 10-20 (invalid: 11), 110-112 (invalid: 111)
        // Expected sum: 11 + 111 = 122
        val result = day02.solvePart2("10-20, 110-112\n")
        assertTrue(result.contains("122"))
    }
}
