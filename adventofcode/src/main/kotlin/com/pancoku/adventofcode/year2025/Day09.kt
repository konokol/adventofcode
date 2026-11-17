package com.pancoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pancoku.adventofcode.solver.DaySolver
import com.pancoku.adventofcode.utils.green
import kotlin.math.abs
import kotlin.math.max

/**
 * Copyright ©2026 [Pancoku]. All rights reserved.
 * Day09
 * <p>
 * @author lijun
 * @since 2026-11-17 14:25
 * @version 1.0
 * 
 */
@AutoService(DaySolver::class)
class Day09: DaySolver {

    override val day = 9
    override val year = 2025

    private data class Point(val x: Long, val y: Long)

    override fun solvePart1(input: String): String {
        val points = input.trim()
            .lines()
            .asSequence()
            .filter { line -> line.isNotBlank() }
            .map { line -> line.split(',').map { s -> s.toLong() } }
            .map { point -> Point(point[0], point[1]) }
            .toList()
        val answer = maxArea(points).toString().green()
        return "\nProcessed ${points.size} pointes. The answer is $answer."
    }

    private fun maxArea(points: List<Point>): Long {
        var maxArea= 0L
        for (i in points.indices) {
            for (j in i + 1..<points.size) {
                maxArea = max(maxArea, abs(points[i].x - points[j].x + 1) * abs(points[i].y - points[j].y + 1))
            }
        }
        return maxArea
    }
}