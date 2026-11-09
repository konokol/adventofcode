package com.pancoku.adventofcode.year2025

import kotlin.test.Test
import kotlin.test.assertTrue

class Day06Test {

    @Test
    fun testDay06Part1() {
        val day06 = Day06()
        val input = """
            123 328  51 64 
             45 64  387 23 
              6 98  215 314
            *   +   *   +  
        """.trimIndent()

        val result = day06.solvePart1(input)
        assertTrue(result.contains("4277556"))
    }

    @Test
    fun testDay06Part2() {
        val day06 = Day06()
        val input = """
            123 328  51 64 
             45 64  387 23 
              6 98  215 314
            *   +   *   +  
        """.trimIndent()

        val result = day06.solvePart2(input).toString()
        assertTrue(result.contains("3263827"))
    }
}
