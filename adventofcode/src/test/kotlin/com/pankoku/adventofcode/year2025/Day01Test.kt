package com.pankoku.adventofcode.year2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {

    @Test
    fun testPassword1() {
        val day01 = Day01()
        val sampleInput = listOf("L50", "R100")
        assertEquals(2, day01.password1(sampleInput))
    }

    @Test
    fun testPassword2() {
        val day01 = Day01()
        val sampleInput = listOf("L50", "R100", "L250")
        assertEquals(4, day01.password2(sampleInput))
    }
}
