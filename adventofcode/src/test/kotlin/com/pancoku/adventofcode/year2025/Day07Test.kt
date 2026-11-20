package com.pancoku.adventofcode.year2025

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day07Test {

    private val day07 = Day07()

    private val sampleInput = """
        .......S.......
        ...............
        .......^.......
        ...............
        ......^.^......
        ...............
        .....^.^.^.....
        ...............
        ....^.^...^....
        ...............
        ...^.^...^.^...
        ...............
        ..^...^.....^..
        ...............
        .^.^.^.^.^...^.
        ...............
    """.trimIndent()

    @Test
    fun testSplitBeam() {
        val lines = sampleInput.lines().filter { it.isNotBlank() }.map { it.toCharArray() }
        val splits = day07.splitBeam(lines)
        assertEquals(21, splits)
    }

    @Test
    fun testCountOfTimelines() {
        val lines = sampleInput.lines().filter { it.isNotBlank() }.map { it.toCharArray() }
        val timelines = day07.countOfTimelines(lines)
        assertEquals(40L, timelines)
    }

    @Test
    fun testDay07Part1() {
        val result = day07.solvePart1(sampleInput)
        assertTrue(result.contains("21"))
    }

    @Test
    fun testDay07Part2() {
        val result = day07.solvePart2(sampleInput)
        assertTrue(result.contains("40"))
    }
}
