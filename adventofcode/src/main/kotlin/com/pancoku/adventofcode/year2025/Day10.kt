package com.pancoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pancoku.adventofcode.solver.DaySolver
import com.pancoku.adventofcode.utils.green
import java.math.BigInteger
import kotlin.math.min

/**
 * Copyright ©2026 [Pancoku]. All rights reserved.
 * Day10
 * <p>
 * @author lijun
 * @since 2026-11-20 14:52
 * @version 1.0
 * 
 */
@AutoService(DaySolver::class)
class Day10 : DaySolver {

    override val day = 10
    override val year = 2025

    data class Machine(
        val targetMask: Int,
        val buttons: List<List<Int>>,
        val targets: List<Long>
    )

    data class State(
        val current: Int,
        val steps: Int
    )

    internal fun parseLine(line: String): Machine {
        // [.##.]
        var targetMask = 0
        line.substringAfter('[').substringBefore(']')
            .forEachIndexed { i, c -> if (c == '#') targetMask = targetMask or (1 shl i) }

        //  (3) (1,3) (2) (2,3) (0,2) (0,1)
        val buttons = line.substringAfter(']').substringBefore('{').trim()
            .split(' ')
            .asSequence()
            .map { it.trim() }
            .map { it.substringAfter('(').substringBefore(')') }
            .map { it.split(',').map { str -> str.trim().toInt() } }
            .toList()

        // {3,5,4,7}
        val targets = line.substringAfter('{').substringBefore('}')
            .split(',')
            .map { it.trim().toLong() }
        return Machine(targetMask, buttons, targets)

    }

    override fun solvePart1(input: String): String {
        val machines = input.trim().lines().filter { it.isNotBlank() }
            .map { parseLine(it) }
        val answer = machines.sumOf { fewestPresses1(it) }.toString().green()
        return "\nProcessed ${machines.size} lines for part 1. The answer is $answer."
    }

    internal fun fewestPresses1(machine: Machine): Int {
        if (machine.targetMask == 0) return 0
        val buttonMasks = machine.buttons.map { indices ->
            indices.fold(0) { acc, idx -> acc or (1 shl idx) }
        }

        val queue = ArrayDeque<State>()
        val visited = HashSet<Int>()

        queue.add(State(0, 0))
        visited.add(0)

        while (queue.isNotEmpty()) {
            val (curr, steps) = queue.removeFirst()
            for (bMask in buttonMasks) {
                val next = curr xor bMask
                if (next == machine.targetMask) return steps + 1
                if (visited.add(next)) {
                    queue.add(State(next, steps + 1))
                }
            }
        }
        return 0
    }

    data class Fraction(val num: BigInteger, val den: BigInteger) {
        constructor(n: Long, d: Long = 1L) : this(BigInteger.valueOf(n), BigInteger.valueOf(d))

        val normalized: Fraction by lazy {
            if (num == BigInteger.ZERO) Fraction(BigInteger.ZERO, BigInteger.ONE)
            else {
                val sign = if (den < BigInteger.ZERO) BigInteger.valueOf(-1) else BigInteger.ONE
                val g = num.abs().gcd(den.abs())
                Fraction(num.multiply(sign).divide(g), den.multiply(sign).divide(g))
            }
        }

        operator fun plus(o: Fraction) = Fraction(num * o.den + o.num * den, den * o.den).normalized
        operator fun minus(o: Fraction) = Fraction(num * o.den - o.num * den, den * o.den).normalized
        operator fun times(o: Fraction) = Fraction(num * o.num, den * o.den).normalized
        operator fun div(o: Fraction) = Fraction(num * o.den, den * o.num).normalized

        fun isZero() = num == BigInteger.ZERO
        fun isNonNegativeInt() = den == BigInteger.ONE && num >= BigInteger.ZERO
        fun toLong() = num.toLong()
    }


    override fun solvePart2(input: String): String {
        val machines = input.trim().lines().filter { it.isNotBlank() }
            .map { parseLine(it) }
        val answer = machines.sumOf { fewestPresses2(it) }.toString().green()
        return "\nProcessed ${machines.size} lines for part 1. The answer is $answer."
    }

    internal fun fewestPresses2(machine: Machine): Long {
        val rows = machine.targets.size
        val cols = machine.buttons.size

        val matrix = Array(rows) { r ->
            Array(cols + 1) { c ->
                when {
                    c == cols -> Fraction(machine.targets[r])
                    r in machine.buttons[c] -> Fraction(1L)
                    else -> Fraction(0L)
                }
            }
        }

        val pivotColToRow = IntArray(cols) { -1 }
        var pivotRow = 0
        for (c in 0 until cols) {
            if (pivotRow >= rows) break
            var sel = -1
            for (r in pivotRow until rows) {
                if (!matrix[r][c].isZero()) {
                    sel = r
                    break
                }
            }
            if (sel == -1) continue

            val temp = matrix[pivotRow]
            matrix[pivotRow] = matrix[sel]
            matrix[sel] = temp

            val divVal = matrix[pivotRow][c]
            for (j in c..cols) matrix[pivotRow][j] = matrix[pivotRow][j] / divVal

            for (r in 0 until rows) {
                if (r != pivotRow && !matrix[r][c].isZero()) {
                    val factor = matrix[r][c]
                    for (j in c..cols) {
                        matrix[r][j] = matrix[r][j] - factor * matrix[pivotRow][j]
                    }
                }
            }
            pivotColToRow[c] = pivotRow
            pivotRow++
        }

        val freeVars = (0 until cols).filter { pivotColToRow[it] == -1 }
        val upperBounds = IntArray(cols) { c ->
            machine.buttons[c].minOfOrNull { machine.targets[it].toInt() } ?: 0
        }

        var minTotalPresses = Long.MAX_VALUE
        val freeValues = LongArray(freeVars.size)

        fun dfs(idx: Int, currentFreeSum: Long) {
            if (currentFreeSum >= minTotalPresses) return // 剪枝

            if (idx == freeVars.size) {
                var total = currentFreeSum
                for (c in 0 until cols) {
                    val r = pivotColToRow[c]
                    if (r != -1) {
                        var valFrac = matrix[r][cols]
                        for (fIdx in freeVars.indices) {
                            val fCol = freeVars[fIdx]
                            if (!matrix[r][fCol].isZero()) {
                                valFrac -= matrix[r][fCol] * Fraction(freeValues[fIdx])
                            }
                        }
                        if (!valFrac.isNonNegativeInt()) return
                        total += valFrac.toLong()
                        if (total >= minTotalPresses) return
                    }
                }
                minTotalPresses = min(minTotalPresses, total)
                return
            }

            val fCol = freeVars[idx]
            for (v in 0..upperBounds[fCol]) {
                freeValues[idx] = v.toLong()
                dfs(idx + 1, currentFreeSum + v)
            }
        }

        dfs(0, 0L)
        return if (minTotalPresses == Long.MAX_VALUE) 0L else minTotalPresses
    }
}