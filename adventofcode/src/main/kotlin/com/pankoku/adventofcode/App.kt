package com.pankoku.adventofcode

import com.pankoku.adventofcode.client.AocInputFetcher

fun main(args: Array<String>) {
    val fetcher = AocInputFetcher(year = 2025)

    println("Welcome to Advent of Code!")
    println("Current year: ${fetcher.year}")
    println("Available commands: 'year [y]' (e.g. year 2025), 'input <day>' (e.g. input 1), 'exit'")

    while (true) {
        print("> ")
        val cmd = readlnOrNull()?.trim() ?: break
        when {
            cmd == "exit" || cmd == "quit" -> {
                println("Bye!")
                break
            }
            cmd == "year" -> {
                println("Current year: ${fetcher.year}")
            }
            cmd.startsWith("year ") -> {
                val yearStr = cmd.removePrefix("year ").trim()
                val newYear = yearStr.toIntOrNull()
                if (newYear == null || newYear < 2015) {
                    println("Please enter a valid year >= 2015 (e.g. 'year 2024')")
                } else {
                    fetcher.year = newYear
                    println("Year changed to ${fetcher.year}")
                }
            }
            cmd.startsWith("input ") -> {
                val dayStr = cmd.removePrefix("input ").trim()
                val day = dayStr.toIntOrNull()
                if (day == null || day !in 1..25) {
                    println("Please enter a valid day between 1 and 25 (e.g. 'input 1')")
                } else {
                    try {
                        val input = fetcher.getInput(day)
                        println("Successfully loaded input for Year ${fetcher.year} Day $day (${input.lines().size} lines).")
                    } catch (e: Exception) {
                        println("Error: ${e.message}")
                    }
                }
            }
            cmd.isEmpty() -> {}
            else -> {
                println("Unknown command: '$cmd'. Use 'year [y]', 'input <day>', or 'exit'.")
            }
        }
    }
}
