package com.pancoku.adventofcode.year2025

import kotlin.test.Test
import kotlin.test.assertTrue

class Day09Test {

    @Test
    fun testDay09Part1Example() {
        val day09 = Day09()
        val input = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
        """.trimIndent()

        val result = day09.solvePart1(input)
        println("PART 1: $result")
        assertTrue(result.contains("50"))
    }

    @Test
    fun testDay09Part2Example() {
        val day09 = Day09()
        val input = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
        """.trimIndent()

        val result = day09.solvePart2(input)
        println("PART 2: $result")
        assertTrue(result.contains("24"))
    }
}
