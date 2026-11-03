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
        val ans = password1(lines)
        return "\nProcessed ${lines.size} lines for Part 1. Answer is $ans".green()
    }

    fun password1(rotations: List<String>): Int {
        var pos = 50
        var cnt0 = 0
        println("The dial starts by pointing at $pos.")
        for (r in rotations) {
            val n: Int = if (r.startsWith("L")) {
                -r.substring(1).toInt()
            } else if (r.startsWith("R")) {
                r.substring(1).toInt()
            } else {
                0
            }
            pos = Math.floorMod(pos + n, 100)
            if (pos == 0) {
                cnt0++
            }
            val msg = if (pos == 0) "0".green() else "$pos"
            println("The dial is rotated $r to point at ${msg}.")
        }
        return cnt0
    }

    override fun solvePart2(input: String): String {
        val lines = input.lines().filter { it.isNotBlank() }
        val ans = password2(lines)
        return "\nProcessed ${lines.size} lines for Part 2. Answer is $ans".green()
    }

    fun password2(rotations: List<String>): Int {
        var pos = 50
        var cnt0 = 0
        println("The dial starts by pointing at $pos.")
        for (r in rotations) {
            val direction = if (r.startsWith("L")) -1 else if (r.startsWith("R")) 1 else 0
            if (direction == 0) continue
            val steps = r.substring(1).toInt()

            val firstHit = if (direction > 0) {
                if (pos == 0) 100 else 100 - pos
            } else {
                if (pos == 0) 100 else pos
            }

            val hits = if (steps >= firstHit) {
                1 + (steps - firstHit) / 100
            } else {
                0
            }

            cnt0 += hits
            pos = Math.floorMod(pos + direction * steps, 100)

            val posMsg = if (pos == 0) "0".green() else "$pos"
            val hitsMsg = when (hits) {
                0 -> ""
                1 -> "; during this rotation, it points at 0 " + "once".green()
                2 -> "; during this rotation, it points at 0 " + "twice".green()
                else -> "; during this rotation, it points at 0 " + "$hits times".green()
            }

            println("The dial is rotated $r to point at ${posMsg}${hitsMsg}.")
        }
        return cnt0
    }
}
