package com.pancoku.adventofcode.year2025

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day09Test {

    private val day09 = Day09()

    private val sampleInput = """
        7,1
        11,1
        11,7
        9,7
        9,5
        2,5
        2,3
        7,3
    """.trimIndent()

    @Test
    fun testCompressCoords() {
        val coords = listOf(2L, 5L, 7L)
        val compressed = day09.compressCoords(coords)
        assertTrue(compressed.isNotEmpty())
    }

    @Test
    fun testMaxArea() {
        val points = listOf(
            Day09.Point(7, 1),
            Day09.Point(11, 1),
            Day09.Point(11, 7)
        )
        val area = day09.maxArea(points)
        assertEquals(15L, area)
    }

    @Test
    fun testMaxArea2() {
        val points = listOf(
            Day09.Point(7, 1),
            Day09.Point(11, 1),
            Day09.Point(11, 7),
            Day09.Point(9, 7),
            Day09.Point(9, 5),
            Day09.Point(2, 5),
            Day09.Point(2, 3),
            Day09.Point(7, 3)
        )
        val area2 = day09.maxArea2(points)
        assertEquals(24L, area2)
    }

    @Test
    fun testDay09Part1Example() {
        val result = day09.solvePart1(sampleInput)
        assertTrue(result.contains("50"))
    }

    @Test
    fun testDay09Part2Example() {
        val result = day09.solvePart2(sampleInput)
        assertTrue(result.contains("24"))
    }
}
