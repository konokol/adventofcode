package com.pankoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pankoku.adventofcode.solver.DaySolver
import com.pankoku.adventofcode.utils.green

/**
 * Copyright ©2026 [pankoku]. All rights reserved.
 * Day02
 * <p>
 * @author lijun
 * @since 2026-11-03 11:47
 * @version 1.0
 * 
 */
@AutoService(DaySolver::class)
class Day02 : DaySolver {

    override val year: Int = 2025
    override val day: Int = 2

    override fun solvePart1(input: String): String {
        val ranges = input.trim().split(",").map { it.trim() }.filter { it.isNotBlank() }
        val ans = invalidSums(ranges)
        return "\nProcessed ${ranges.size} ranges in Part 1. Answer is $ans".green()
    }

    private fun invalidSums(ranges: List<String>): Long {
        var sum: Long = 0
        for (rawRange in ranges) {
            val parts = rawRange.split("-")
            if (parts.size < 2) continue
            val start = parts[0].trim().toLongOrNull() ?: continue
            val end = parts[1].trim().toLongOrNull() ?: continue

            val list = mutableListOf<String>()
            for (n in start..end) {
                val s = n.toString()
                if (s.length % 2 != 0) continue
                val half = s.length / 2
                if (s.substring(0, half) == s.substring(half)) {
                    list.add(n.toString())
                    sum += n
                }
            }
            val msg = when {
                list.isEmpty() -> "contains no invalid IDs."
                list.size == 1 -> "has ${"1".green()} invalid ID: ${list.first()}."
                else -> "has ${list.size.toString().green()} invalid IDs: ${list.joinToString(", ")}."
            }
            println("$rawRange $msg")
        }
        return sum
    }

    override fun solvePart2(input: String): String {
        val ranges = input.trim().split(",").map { it.trim() }.filter { it.isNotBlank() }
        val ans = invalidSums2(ranges)
        return "\nProcessed ${ranges.size} ranges in Part 2. Answer is $ans".green()
    }

    private fun invalidSums2(rawRanges: List<String>): Long {
        val ranges = rawRanges.mapNotNull { raw ->
            val parts = raw.split("-")
            if (parts.size >= 2) {
                val s = parts[0].trim().toLongOrNull()
                val e = parts[1].trim().toLongOrNull()
                if (s != null && e != null) s..e else null
            } else null
        }

        if (ranges.isEmpty()) return 0L

        val minStart = ranges.minOf { it.first }
        val maxEnd = ranges.maxOf { it.last }
        val maxLength = maxEnd.toString().length

        val invalidIds = mutableSetOf<Long>()

        for (totalLen in 2..maxLength) {
            for (patternLen in 1..totalLen / 2) {
                if (totalLen % patternLen != 0) continue
                val repeatCount = totalLen / patternLen

                val start = pow(10, patternLen - 1)
                val end = pow(10, patternLen) - 1

                for (pattern in start..end) {
                    val num = pattern.toString().repeat(repeatCount).toLongOrNull() ?: continue

                    if (num in minStart..maxEnd) {
                        if (ranges.any { num in it }) {
                            invalidIds.add(num)
                        }
                    }
                }
            }
        }
        return invalidIds.sum()
    }

    private fun pow(base: Int, k: Int): Long {
        var res = 1L
        repeat(k) { res *= base }
        return res
    }
}
