package com.pankoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pankoku.adventofcode.solver.DaySolver
import com.pankoku.adventofcode.utils.green

/**
 * Copyright ©2026 [Pancoku]. All rights reserved.
 * Day06
 * <p>
 * @author lijun
 * @since 2026-11-07 14:39
 * @version 1.0
 * 
 */
@AutoService(DaySolver::class)
class Day06 : DaySolver {

    override val day = 6
    override val year = 2025

    override fun solvePart1(input: String): Any {
        val allLines = input.lines().filter { it.isNotBlank() }
        val nums = allLines.subList(0, allLines.size - 1).map { it.trim().split(Regex("\\s+")) }
        val ops = allLines.last().trim().split(Regex("\\s+"))
        val ans = addGrandTotal(nums, ops)
        return "\nProcessed ${allLines.size} lines for Part 1. Answer is $ans".green()
    }

    private fun addGrandTotal(nums: List<List<String>>, ops: List<String>): Long {
        if (nums.isEmpty() || nums[0].size != ops.size) {
            return -1
        }
        var sum = 0L
        for (col in nums[0].indices) {
            val op = ops[col]
            var grand: Long = -1L
            for (row in nums.indices) {
                grand = if (grand == -1L) {
                    nums[row][col].toLong()
                } else {
                    when (op) {
                        "*" -> grand * nums[row][col].toLong()
                        "+" -> grand + nums[row][col].toLong()
                        else -> -1
                    }
                }
            }
            println("The grand is $grand for column $col.")
            sum += grand
        }
        return sum
    }
}
