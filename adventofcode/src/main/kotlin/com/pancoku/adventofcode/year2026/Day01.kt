package com.pancoku.adventofcode.year2026

import com.google.auto.service.AutoService
import com.pancoku.adventofcode.solver.AOC
import com.pancoku.adventofcode.solver.AbsDaySolver
import com.pancoku.adventofcode.solver.DaySolver

@AutoService(DaySolver::class)
@AOC(year = 2026, day = 1)
class Day01 : AbsDaySolver() {
    override fun solvePart1(input: String): String {
        val lines = input.lines().filter { it.isNotBlank() }
        return "Processed ${lines.size} lines for Part 1"
    }

    override fun solvePart2(input: String): Any {
        val lines = input.lines().filter { it.isNotBlank() }
        return "Processed ${lines.size} lines for Part 2"
    }
}
