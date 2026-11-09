package com.pancoku.adventofcode.client

import com.pancoku.adventofcode.command.AocCommand
import com.pancoku.adventofcode.solver.SolverRegistry
import com.pancoku.adventofcode.utils.green
import com.pancoku.adventofcode.utils.red
import kotlin.system.measureTimeMillis

class AocClient(
    val inputFetcher: AocInputFetcher = AocInputFetcher(),
    val solverRegistry: SolverRegistry = SolverRegistry()
) {
    var year: Int
        get() = inputFetcher.year
        set(value) {
            inputFetcher.year = value
        }

    /**
     * Executes an [AocCommand].
     * @return true to continue REPL loop, false on Exit command.
     */
    fun execute(command: AocCommand): Boolean {
        when (command) {
            is AocCommand.Exit -> {
                println("Bye!")
                return false
            }
            is AocCommand.ShowYear -> {
                println("Current year: $year")
            }
            is AocCommand.SetYear -> {
                year = command.year
                println("Year set to $year")
            }
            is AocCommand.FetchInput -> {
                val targetYear = command.year ?: year
                try {
                    val input = inputFetcher.getInput(command.day, targetYear)
                    val lineCount = input.lines().size
                    println("Successfully loaded input for Year $targetYear Day ${command.day} ($lineCount lines).")
                } catch (e: Exception) {
                    println("Error fetching input: ${e.message}")
                }
            }
            is AocCommand.Solve -> {
                val targetYear = command.year ?: year
                val day = command.day
                val solver = solverRegistry.getSolver(targetYear, day)
                if (solver == null) {
                    println("No solver registered for Year $targetYear Day $day.")
                    return true
                }

                try {
                    val input = inputFetcher.getInput(day, targetYear).trimIndent()
                    if (command.part == null || command.part == 1) {
                        var res1: Any?
                        val time1 = measureTimeMillis {
                            res1 = solver.solvePart1(input)
                        }
                        println("Year $targetYear Day $day Part 1: $res1 (took ${time1}ms)")
                    }
                    if (command.part == null || command.part == 2) {
                        var res2: Any?
                        val time2 = measureTimeMillis {
                            res2 = solver.solvePart2(input)
                        }
                        println("Year $targetYear Day $day Part 2: $res2 (took ${time2}ms)")
                    }
                } catch (e: Exception) {
                    println("Error solving Year $targetYear Day $day: ${e.message}")
                    e.printStackTrace()
                }
            }
            is AocCommand.Help -> {
                printHelp()
            }
            is AocCommand.Unknown -> {
                if (command.error.isNotEmpty()) {
                    println(command.error)
                }
            }
        }
        return true
    }

    private fun printHelp() {
        println(
            """
            Available Commands:
              year            - Show currently active year
              year <y>        - Change active year (e.g., 'year 2024')
              input <day>     - Fetch or load input for specified day (e.g., 'input 1')
              solve <day> [p] - Solve day problem (Part 1, Part 2, or both) (e.g., 'solve 1' or 'solve 1 2')
              help            - Display this help message
              exit            - Exit the application
            """.trimIndent()
        )
    }
}
