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
}
