package com.pankoku.adventofcode.year2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {

    @Test
    fun testMaxJoltageForLine() {
        val day03 = Day03()

        assertEquals(91, day03.largestJoltage1(listOf("891")))
        assertEquals(98, day03.largestJoltage1(listOf("9812")))
        assertEquals(91, day03.largestJoltage1(listOf("23491")))
    }

    @Test
    fun testLargestJoltageSum() {
        val day03 = Day03()
        val lines = listOf("891", "9812", "23491")
        // 91 + 98 + 91 = 280
        assertEquals(280, day03.largestJoltage1(lines))
    }

    @Test
    fun testMaxJoltage2ForLine() {
        val day03 = Day03()

        assertEquals(987654321111L, day03.largestJoltage2(listOf("987654321111111")))
        assertEquals(811111111119L, day03.largestJoltage2(listOf("811111111111119")))
        assertEquals(434234234278L, day03.largestJoltage2(listOf("234234234234278")))
        assertEquals(888911112111L, day03.largestJoltage2(listOf("818181911112111")))
    }

    @Test
    fun testLargestJoltage2Sum() {
        val day03 = Day03()
        val lines = listOf(
            "987654321111111",
            "811111111111119",
            "234234234234278",
            "818181911112111"
        )
        // 987654321111 + 811111111119 + 434234234278 + 888911112111 = 3121910778619
        assertEquals(3121910778619L, day03.largestJoltage2(lines))
    }
}
