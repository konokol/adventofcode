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
        println(result)
        assertTrue(result.contains("50"))
    }
}
