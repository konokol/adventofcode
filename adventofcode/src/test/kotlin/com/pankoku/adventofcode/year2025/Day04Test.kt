package com.pankoku.adventofcode.year2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test {

    @Test
    fun testDay04Part1Example() {
        val day04 = Day04()
        val input = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
        """.trimIndent()

        val grid = input.lines()
            .filter { it.isNotBlank() }
            .map { it.toCharArray() }
            .toTypedArray()

        val ans = day04.countOfRollsToAccess(grid)
        assertEquals(13, ans)
    }

    @Test
    fun testDay04Part2Example() {
        val day04 = Day04()
        val input = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
        """.trimIndent()

        val grid = input.lines()
            .filter { it.isNotBlank() }
            .map { it.toCharArray() }
            .toTypedArray()

        val ans = day04.removeRollsOfPapers(grid)
        assertEquals(43, ans)
    }
}
