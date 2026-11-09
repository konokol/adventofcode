package com.pancoku.adventofcode.year2025

import kotlin.test.Test
import kotlin.test.assertTrue

class Day05Test {

    @Test
    fun testDay05Part1Example() {
        val day05 = Day05()
        val input = """
            3-5
            10-14
            16-20
            12-18

            1
            5
            8
            11
            17
            32
        """.trimIndent()

        val result = day05.solvePart1(input)
        assertTrue(result.contains("3"))
    }

    @Test
    fun testDay05Part2Example() {
        val day05 = Day05()
        val input = """
            3-5
            10-14
            16-20
            12-18

            1
            5
            8
            11
            17
            32
        """.trimIndent()

        val result = day05.solvePart2(input).toString()
        assertTrue(result.contains("14"))
    }
}
