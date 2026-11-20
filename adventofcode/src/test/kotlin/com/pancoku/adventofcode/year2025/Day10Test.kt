package com.pancoku.adventofcode.year2025

import kotlin.test.Test
import kotlin.test.assertTrue

class Day10Test {

    @Test
    fun testDay10Part1Example() {
        val day10 = Day10()
        val input = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """.trimIndent()

        val result = day10.solvePart1(input)
        println("PART 1: $result")
        assertTrue(result.contains("7"))
    }
}
