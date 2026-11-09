package com.pancoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pancoku.adventofcode.solver.DaySolver
import com.pancoku.adventofcode.utils.green
import okhttp3.internal.toLongOrDefault
import kotlin.math.max

/**
 * Copyright ©2026 [Pancoku]. All rights reserved.
 * Day05
 * <p>
 * @author lijun
 * @since 2026-11-06 11:07
 * @version 1.0
 * 
 */
@AutoService(DaySolver::class)
class Day05 : DaySolver {

    override val day = 5
    override val year = 2025

    private data class Input(val ranges: List<LongRange>, val ids: List<Long>)

    private fun resolveInput(input: String): Input {
        val parts = input.trim().split(Regex("\\r?\\n\\r?\\n"))
        if (parts.size < 2) {
            return Input(listOf(), listOf())
        }
        val ranges = parts[0].lines()
            .filter { it.isNotBlank() }
            .map { it.split('-') }
            .map { it[0].toLong()..it[1].toLong() }
        val ids = parts[1].lines()
            .filter { it.isNotBlank() }
            .map { it.toLongOrDefault(0) }
        return Input(ranges, ids)
    }

    override fun solvePart1(input: String): String {
        val (ranges, ids) = resolveInput(input)
        val ans = freshIngredientInRange1(ranges, ids).toString().green()
        return "\nProcessed ${ranges.size} ranges and ${ids.size} ids for part 1. " +
                "$ans available ingredient IDs are fresh."
    }

    fun freshIngredientInRange1(ranges: List<LongRange>, ids: List<Long>): Int {
        val merged = mergerRanges(ranges)
        val sum = ids.count { find(merged, it) }
        return sum
    }

    private fun find(ranges: List<LongRange>, target: Long): Boolean {
        if (ranges.isEmpty()) {
            return false
        }
        if (target < ranges.first().first || target > ranges.last().last) {
            // out of range
            return false
        }
        var left = 0
        var right = ranges.size - 1
        while (left < right) {
            val mid = left + (right - left + 1) / 2
            if (target < ranges[mid].first) {
                right = mid - 1
            } else {
                left = mid
            }
        }
        return target in ranges[left]
    }

    private fun mergerRanges(ranges: List<LongRange>): List<LongRange> {
        if (ranges.isEmpty()) {
            return ranges
        }
        val sorted = ranges.sortedWith { l1, l2 -> when {
            l1.first < l2.first -> -1
            l1.first > l2.first -> 1
            else -> 0
        } }
        val list = mutableListOf<LongRange>()
        var start = sorted[0].first
        var end = sorted[0].last
        for (i in 1 until sorted.size) {
            val r = sorted[i]
            if (r.first <= end) {
                // merge
                end = max(r.last, end)
            } else {
                // add
                list.add(start..end)
                start = r.first
                end = r.last
            }
        }
        list.add(start..end)
        return list
    }

    override fun solvePart2(input: String): Any {
        val (ranges) = resolveInput(input)
        val ans = freshIngredientInRange2(ranges).toString().green()
        return "\nProcessed ${ranges.size} ranges for part 2. " +
                "$ans ingredient IDs are considered to be fresh according to the fresh ingredient ID ranges."
    }

    private fun freshIngredientInRange2(ranges: List<LongRange>): Long {
        val merged = mergerRanges(ranges)
        return merged.sumOf { it.last - it.first + 1 }
    }
}
