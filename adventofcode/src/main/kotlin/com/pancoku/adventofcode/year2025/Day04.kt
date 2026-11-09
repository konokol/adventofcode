package com.pancoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pancoku.adventofcode.solver.DaySolver
import com.pancoku.adventofcode.utils.green

/**
 * Copyright ©2026 [Pancoku]. All rights reserved.
 * Day04
 * <p>
 * @author lijun
 * @since 2026-11-05 09:56
 * @version 1.0
 * 
 */
@AutoService(DaySolver::class)
class Day04 : DaySolver {
    override val day = 4
    override val year = 2025

    val directions = listOf(
        intArrayOf(-1, -1), intArrayOf(0, -1), intArrayOf(1, -1),
        intArrayOf(-1, 0), intArrayOf(1, 0),
        intArrayOf(-1, 1), intArrayOf(0, 1), intArrayOf(1, 1)
    )

    override fun solvePart1(input: String): Any {
        val grid = input.lines()
            .filter { it.isNotBlank() }
            .map { it.toCharArray() }
            .toTypedArray()
        val ans = countOfRollsToAccess(grid).toString().green()
        return "\nSize of grid is ${grid.size}x${grid[0].size} for Part 1. " +
                "$ans rolls of paper can be accessed by a forklift"
    }

    fun countOfRollsToAccess(matrix: Array<CharArray>): Int {
        val cnt = matrix.indices.sumOf { row ->
            matrix[row].indices.count { col ->
                matrix[row][col] == '@' && directions.count { dir ->
                    val targetR = row + dir[0]
                    val targetC = col + dir[1]
                    targetR in matrix.indices && targetC in matrix[0].indices && matrix[targetR][targetC] == '@'
                } < 4
            }
        }
        return cnt
    }

    override fun solvePart2(input: String): Any {
        val grid = input.lines()
            .filter { it.isNotBlank() }
            .map { it.toCharArray() }
            .toTypedArray()
        val ans = removeRollsOfPapers(grid).toString().green()
        return "\nSize of grid is ${grid.size}x${grid[0].size} for Part 1." +
                " $ans rolls of paper in total can be removed by the Elves and their forklifts"
    }

    fun removeRollsOfPapers(matrix: Array<CharArray>): Int {
        var sum = 0
        while (true) {
            val cnt = matrix.indices.sumOf { row ->
                matrix[row].indices.count { col ->
                    val remove = (matrix[row][col] == '@' || matrix[row][col] == 'X') &&
                            directions.count { dir ->
                                val targetR = row + dir[0]
                                val targetC = col + dir[1]
                                targetR in matrix.indices && targetC in matrix[0].indices &&
                                        (matrix[targetR][targetC] == '@' || matrix[targetR][targetC] == 'X')
                            } < 4
                    if (remove) {
                        matrix[row][col] = 'X'
                    }
                    return@count remove
                }
            }
            sum += cnt
            if (cnt == 0) {
                println("No more roll of paper to remove!")
                break
            } else {
                matrix.indices.forEach { row ->
                    matrix[row].indices.forEach { col ->
                        if (matrix[row][col] == 'X') {
                            matrix[row][col] = '.'
                        }
                    }
                }
                println("Remove $cnt rolls of paper...")
            }
        }
        return sum
    }
}
