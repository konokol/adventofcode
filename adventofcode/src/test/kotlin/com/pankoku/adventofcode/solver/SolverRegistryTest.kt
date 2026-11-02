package com.pankoku.adventofcode.solver

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SolverRegistryTest {

    @Test
    fun testDynamicOnDemandSolverLookup() {
        val registry = SolverRegistry()

        // 2025 Day 1 is Day01Solver in package com.pankoku.adventofcode.year2025
        val solver = registry.getSolver(2025, 1)

        assertNotNull(solver, "Dynamic on-demand loading should locate Day01Solver for 2025 Day 1")
        assertEquals(2025, solver.year)
        assertEquals(1, solver.day)

        val result1 = solver.solvePart1("line1\nline2")
        assertEquals("Processed 2 lines for Part 1", result1)
    }

    @Test
    fun testNonExistentSolverReturnsNull() {
        val registry = SolverRegistry()
        val solver = registry.getSolver(1999, 99)
        assertNull(solver, "Non-existent solver should return null without crashing")
    }
}
