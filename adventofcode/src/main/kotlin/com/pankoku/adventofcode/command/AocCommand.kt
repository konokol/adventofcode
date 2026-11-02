package com.pankoku.adventofcode.command

sealed interface AocCommand {
    data class SetYear(val year: Int) : AocCommand
    data class FetchInput(val day: Int, val year: Int? = null) : AocCommand
    data class Solve(val day: Int, val part: Int? = null, val year: Int? = null) : AocCommand
    data object ShowYear : AocCommand
    data object Help : AocCommand
    data object Exit : AocCommand
    data class Unknown(val rawInput: String, val error: String) : AocCommand

    companion object Factory {
        fun create(rawInput: String): AocCommand {
            val trimmed = rawInput.trim()
            if (trimmed.isEmpty()) return Unknown(rawInput, "")

            val tokens = trimmed.split("\\s+".toRegex())
            return when (val action = tokens[0].lowercase()) {
                "exit", "quit" -> Exit
                "help" -> Help
                "year" -> when {
                    tokens.size == 1 -> ShowYear
                    else -> {
                        val parsedYear = tokens[1].toIntOrNull()
                        if (parsedYear != null && parsedYear >= 2015) {
                            SetYear(parsedYear)
                        } else {
                            Unknown(rawInput, "Invalid year '${tokens[1]}'. Year must be a number >= 2015.")
                        }
                    }
                }
                "input", "fetch" -> when {
                    tokens.size > 1 && tokens[1].toIntOrNull() != null -> {
                        val day = tokens[1].toInt()
                        if (day in 1..25) {
                            val targetYear = tokens.getOrNull(2)?.toIntOrNull()
                            FetchInput(day = day, year = targetYear)
                        } else {
                            Unknown(rawInput, "Day must be between 1 and 25.")
                        }
                    }
                    else -> Unknown(rawInput, "Usage: input <day> [year] (e.g., 'input 1' or 'input 1 2024')")
                }
                "solve" -> when {
                    tokens.size > 1 && tokens[1].toIntOrNull() != null -> {
                        val day = tokens[1].toInt()
                        if (day in 1..25) {
                            val part = tokens.getOrNull(2)?.toIntOrNull()
                            val year = tokens.getOrNull(3)?.toIntOrNull()
                            Solve(day = day, part = part, year = year)
                        } else {
                            Unknown(rawInput, "Day must be between 1 and 25.")
                        }
                    }
                    else -> Unknown(rawInput, "Usage: solve <day> [part] [year] (e.g., 'solve 1' or 'solve 1 2')")
                }
                else -> Unknown(rawInput, "Unknown command '$action'. Type 'help' for available commands.")
            }
        }
    }
}
