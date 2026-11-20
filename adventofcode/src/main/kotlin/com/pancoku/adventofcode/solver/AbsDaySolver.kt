package com.pancoku.adventofcode.solver

abstract class AbsDaySolver : DaySolver {
    private val aocAnnotation: AOC by lazy {
        this::class.java.annotations.filterIsInstance<AOC>().firstOrNull()
            ?: throw IllegalStateException("Class ${this::class.simpleName} must be annotated with @AOC(year = ..., day = ...)")
    }

    override val year: Int get() = aocAnnotation.year
    override val day: Int get() = aocAnnotation.day
}
