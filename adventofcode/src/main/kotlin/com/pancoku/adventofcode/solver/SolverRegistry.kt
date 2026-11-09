package com.pancoku.adventofcode.solver

import java.util.ServiceLoader

class SolverRegistry {
    private val cachedSolvers = mutableMapOf<Pair<Int, Int>, DaySolver>()

    fun register(solver: DaySolver) {
        cachedSolvers[Pair(solver.year, solver.day)] = solver
    }

    /**
     * Gets a [DaySolver] for the specified year and day.
     * Looks up in-memory cache first, otherwise triggers ServiceLoader on-demand when a command is executed.
     */
    fun getSolver(year: Int, day: Int): DaySolver? {
        val key = Pair(year, day)
        if (cachedSolvers.containsKey(key)) {
            return cachedSolvers[key]
        }

        val solver = loadSolverViaServiceLoader(year, day)
        if (solver != null) {
            cachedSolvers[key] = solver
        }
        return solver
    }

    private fun loadSolverViaServiceLoader(year: Int, day: Int): DaySolver? {
        val loader = ServiceLoader.load(DaySolver::class.java, DaySolver::class.java.classLoader)
        for (solver in loader) {
            val solverKey = Pair(solver.year, solver.day)
            cachedSolvers[solverKey] = solver
            if (solver.year == year && solver.day == day) {
                return solver
            }
        }
        return null
    }
}
