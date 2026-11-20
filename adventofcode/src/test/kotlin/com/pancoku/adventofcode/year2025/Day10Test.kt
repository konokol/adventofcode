package com.pancoku.adventofcode.year2025

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import java.math.BigInteger

class Day10Test {

    private val day10 = Day10()

    @Test
    fun testParseLine() {
        val line = "[.##.] (3) (1,3) (2) {3,5,4,7}"
        val machine = day10.parseLine(line)
        assertEquals(6, machine.targetMask)
        assertEquals(3, machine.buttons.size)
        assertEquals(listOf(3L, 5L, 4L, 7L), machine.targets)
    }

    @Test
    fun testFewestPresses1() {
        val line = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"
        val machine = day10.parseLine(line)
        val presses = day10.fewestPresses1(machine)
        assertEquals(2, presses)
    }

    @Test
    fun testFewestPresses2() {
        val line = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"
        val machine = day10.parseLine(line)
        val presses = day10.fewestPresses2(machine)
        assertEquals(10, presses)
    }

    @Test
    fun testFractionOperations() {
        val f1 = Day10.Fraction(3L, 2L)
        val f2 = Day10.Fraction(1L, 2L)
        val sum = f1 + f2
        assertEquals(2L, sum.toLong())

        val f3 = Day10.Fraction(BigInteger.valueOf(10), BigInteger.valueOf(5))
        assertEquals(2L, f3.toLong())
        assertTrue(f3.isNonNegativeInt())
    }

    @Test
    fun testDay10Part1Example() {
        val input = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """.trimIndent()

        val result = day10.solvePart1(input)
        assertTrue(result.contains("7"))
    }

    @Test
    fun testDay10Part2Example() {
        val input = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """.trimIndent()

        val result = day10.solvePart2(input)
        assertTrue(result.contains("33"))
    }
}
