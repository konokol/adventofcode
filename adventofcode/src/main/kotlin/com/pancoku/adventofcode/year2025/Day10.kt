package com.pancoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pancoku.adventofcode.solver.DaySolver
import com.pancoku.adventofcode.utils.green

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

    private fun parseLine(line: String): Machine {
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
        val answer = fewestPressesOnAllMachines(machines).toString().green()
        return "\nProcessed ${machines.size} lines for part 1. The answer is $answer."
    }

    private fun fewestPressesOnAllMachines(machines: List<Machine>): Int {
        return machines.sumOf { fewestPresses(it) }
    }

    private fun fewestPresses(machine: Machine): Int {
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
}