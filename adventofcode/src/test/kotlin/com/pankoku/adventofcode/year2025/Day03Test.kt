package com.pankoku.adventofcode.year2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {

    @Test
    fun testMaxJoltageForLine() {
        val day03 = Day03()

        assertEquals(91, day03.largestJoltage(listOf("891")))
        assertEquals(98, day03.largestJoltage(listOf("9812")))
        assertEquals(91, day03.largestJoltage(listOf("23491")))
    }

    @Test
    fun testLargestJoltageSum() {
        val day03 = Day03()
        val lines = listOf("891", "9812", "23491")
        // 91 + 98 + 91 = 280
        assertEquals(280, day03.largestJoltage(lines))
    }
}
