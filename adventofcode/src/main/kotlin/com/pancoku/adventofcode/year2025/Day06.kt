package com.pancoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pancoku.adventofcode.solver.DaySolver
import com.pancoku.adventofcode.utils.green

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

    override fun solvePart1(input: String): String {
        val allLines = input.lines().filter { it.isNotBlank() }
        val numsVert = allLines.subList(0, allLines.size - 1)
            .map {
                it.trim()
                    .split(Regex("\\s+"))
                    .map { s -> s.toLong() }
            }
        val nums = mutableListOf<MutableList<Long>>()
        for (c in numsVert[0].indices) {
            for (r in numsVert.indices) {
                if (r == 0) {
                    nums.add(mutableListOf())
                }
                nums.last().add(numsVert[r][c])
            }
        }
        val ops = allLines.last().trim().split(Regex("\\s+"))
        val ans = addGrandTotal(nums, ops)
        return "\nProcessed ${allLines.size} lines for Part 1. Answer is $ans".green()
    }

    private fun addGrandTotal(nums: List<List<Long>>, ops: List<String>): Long {
        if (nums.isEmpty() || nums.size != ops.size) {
            return -1
        }
        var sum = 0L
        for (row in nums.indices) {
            val op = ops[row]
            var grand: Long = -1L
            for (col in nums[row].indices) {
                grand = if (grand == -1L) {
                    nums[row][col]
                } else {
                    when (op) {
                        "*" -> grand * nums[row][col]
                        "+" -> grand + nums[row][col]
                        else -> -1
                    }
                }
            }
            println("Col ${row}: ${nums[row].joinToString(op)}=$grand")
            sum += grand
        }
        return sum
    }

    override fun solvePart2(input: String): Any {
        val allLines = input.lines().filter { it.isNotBlank() }
        val numLines = allLines.subList(0, allLines.size - 1)
        val nums: MutableList<List<Long>> = mutableListOf()
        var list: MutableList<Long>? = null
        for (c in numLines[0].length - 1 downTo 0) {
            if (list == null) {
                list = mutableListOf()
            }
            val builder = StringBuilder()
            for (r in numLines.indices) {
                builder.append(numLines[r][c])
            }
            if (builder.trim().isEmpty()) {
                nums.add(list)
                list = null
            } else {
                list.add(builder.trim().toString().toLong())
                if (c == 0) {
                    nums.add(list)
                    list = null
                }
            }
        }
        val ops = allLines.last().trim().split(Regex("\\s+")).reversed()
        val ans = addGrandTotal(nums, ops)
        return "\nProcessed ${allLines.size} lines for Part 2. Answer is $ans".green()
    }
}
