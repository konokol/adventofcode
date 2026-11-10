package com.pancoku.adventofcode.year2025

import kotlin.test.Test
import kotlin.test.assertTrue

class Day08Test {

    @Test
    fun testDay08part1() {
        val day08 = Day08()
        val input = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
        """.trimIndent()
        val result = day08.solvePart1(input)
        println(result)
        assertTrue(result.contains("20"))
    }

    @Test
    fun testDay08part2() {
        val day08 = Day08()
        val input = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
        """.trimIndent()
        val result = day08.solvePart2(input)
        println(result)
        assertTrue(result.contains("25272"))
    }
}
