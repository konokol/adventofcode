package com.pancoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pancoku.adventofcode.solver.DaySolver
import com.pancoku.adventofcode.utils.green
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

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
        return "\nProcessed ${points.size} pointes for part 1. The answer is $answer."
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

    override fun solvePart2(input: String): String {
        val points = input.trim()
            .lines()
            .asSequence()
            .filter { line -> line.isNotBlank() }
            .map { line -> line.split(',').map { s -> s.toLong() } }
            .map { point -> Point(point[0], point[1]) }
            .toList()
        val answer = maxArea2(points).toString().green()
        return "\nProcessed ${points.size} pointes for part 2. The answer is $answer."
    }

    private fun maxArea2(points: List<Point>): Long {
        // 1. compress
        val xs = compressCoords(points.map { it.x })
        val ys = compressCoords(points.map { it.y })
        val xToIdx = xs.withIndex().associate { it.value to it.index }
        val yToIdx = ys.withIndex().associate { it.value to it.index }

        val width = xs.size
        val height = ys.size

        val grid = Array(height) { IntArray(width) }

        // 2. fill inside
        for (i in points.indices) {
            val p1 = points[i]
            val p2 = points[(i + 1) % points.size]
            val x1 = xToIdx[p1.x]!!; val y1 = yToIdx[p1.y]!!
            val x2 = xToIdx[p2.x]!!; val y2 = yToIdx[p2.y]!!

            for (y in min(y1, y2)..max(y1, y2)) {
                for (x in min(x1, x2)..max(x1, x2)) {
                    grid[y][x] = 1
                }
            }
        }

        // 3. fill outside
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(0 to 0)
        grid[0][0] = 2

        val dirs = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
        while (queue.isNotEmpty()) {
            val (cx, cy) = queue.removeFirst()
            for ((dx, dy) in dirs) {
                val nx = cx + dx
                val ny = cy + dy
                if (nx in 0 until width && ny in 0 until height && grid[ny][nx] == 0) {
                    grid[ny][nx] = 2
                    queue.add(nx to ny)
                }
            }
        }

        // 4. prefix sum
        val prefixSum = Array(height + 1) { IntArray(width + 1) }
        for (y in 0 until height) {
            for (x in 0 until width) {
                val isValid = if (grid[y][x] != 2) 1 else 0
                prefixSum[y + 1][x + 1] = isValid + prefixSum[y][x + 1] + prefixSum[y + 1][x] - prefixSum[y][x]
            }
        }

        // 5. check
        var maxArea = 0L
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val p1 = points[i]
                val p2 = points[j]
                val actualArea = (abs(p1.x - p2.x) + 1) * (abs(p1.y - p2.y) + 1)
                if (actualArea <= maxArea) continue

                val x1 = min(xToIdx[p1.x]!!, xToIdx[p2.x]!!)
                val x2 = max(xToIdx[p1.x]!!, xToIdx[p2.x]!!)
                val y1 = min(yToIdx[p1.y]!!, yToIdx[p2.y]!!)
                val y2 = max(yToIdx[p1.y]!!, yToIdx[p2.y]!!)

                val expectedCells = (x2 - x1 + 1) * (y2 - y1 + 1)
                val validCells = prefixSum[y2 + 1][x2 + 1] - prefixSum[y1][x2 + 1] - prefixSum[y2 + 1][x1] + prefixSum[y1][x1]

                if (validCells == expectedCells) {
                    maxArea = actualArea
                }
            }
        }

        return maxArea
    }

    private fun compressCoords(coords: List<Long>): List<Long> {
        val unique = coords.distinct().sorted()
        val result = mutableListOf<Long>()
        result.add(unique.first() - 1)
        for (i in unique.indices) {
            result.add(unique[i])
            if (i + 1 < unique.size && unique[i + 1] - unique[i] > 1) {
                result.add(unique[i] + 1)
            }
        }
        result.add(unique.last() + 1)
        return result
    }

}