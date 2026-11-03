package com.pankoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pankoku.adventofcode.solver.DaySolver
import com.pankoku.adventofcode.utils.green

@AutoService(DaySolver::class)
class Day01 : DaySolver {
    override val year: Int = 2025
    override val day: Int = 1

    override fun solvePart1(input: String): String {
        val lines = input.lines().filter { it.isNotBlank() }
        val ans = password(lines)
        return "\nProcessed ${lines.size} lines for Part 1. Answer is $ans".green()
    }

    fun password(rotations: List<String>): Int {
        var pos = 50
        var cnt0 = 0
        println("The dial starts by pointing at $pos.")
        for (r in rotations) {
            val n: Int = if (r.startsWith("L")) {
                - r.substring(1).toInt() % 100
            } else if (r.startsWith("R")) {
                r.substring(1).toInt() % 100
            } else {
                0
            }
            pos = (pos + n + 100) % 100
            if (pos == 0) {
                cnt0++
            }
            val msg = if (pos == 0) "${pos}".green() else "$pos"
            println("The dial is rotated $r to point at ${msg}.")
        }
        return cnt0
    }

    override fun solvePart2(input: String): Any {
        val lines = input.lines().filter { it.isNotBlank() }
        return "Processed ${lines.size} lines for Part 2"
    }
}
