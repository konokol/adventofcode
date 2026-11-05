package com.pankoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pankoku.adventofcode.solver.DaySolver
import com.pankoku.adventofcode.utils.green

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
        intArrayOf(-1,  0),                   intArrayOf(1,  0),
        intArrayOf(-1,  1), intArrayOf(0,  1), intArrayOf(1,  1)
    )

    override fun solvePart1(input: String): Any {
        val grid = input.lines()
            .filter { it.isNotBlank() }
            .map { it.toCharArray() }
            .toTypedArray()
        val ans = countOfRollsToAccess(grid).toString().green()
        return "Size of grid is ${grid.size}x${grid[0].size}, answer is $ans"
    }

    fun countOfRollsToAccess(matrix: Array<CharArray>): Int {
        val cnt = matrix.indices.sumOf { row ->
            matrix[row].indices.count { col ->
                matrix[row][col] == '@' && check(matrix, row, col)
            }
        }
        return cnt
    }

    private fun check(matrix: Array<CharArray>, r: Int, c: Int): Boolean {
        return directions.count { dir ->
            val targetR = r + dir[0]
            val targetC = c + dir[1]
            targetR in matrix.indices && targetC in matrix[0].indices && matrix[targetR][targetC] == '@'
        } < 4
    }
}
