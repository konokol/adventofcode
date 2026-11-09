package com.pancoku.adventofcode.year2026

import com.google.auto.service.AutoService
import com.pancoku.adventofcode.solver.DaySolver

@AutoService(DaySolver::class)
class Day01 : DaySolver {
    override val year: Int = 2026
    override val day: Int = 1

    override fun solvePart1(input: String): String {
        val lines = input.lines().filter { it.isNotBlank() }
        return "Processed ${lines.size} lines for Part 1"
    }

    override fun solvePart2(input: String): Any {
        val lines = input.lines().filter { it.isNotBlank() }
        return "Processed ${lines.size} lines for Part 2"
    }
}
