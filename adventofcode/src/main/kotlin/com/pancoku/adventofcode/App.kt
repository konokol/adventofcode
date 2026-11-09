package com.pancoku.adventofcode

import com.pancoku.adventofcode.client.AocClient
import com.pancoku.adventofcode.client.AocInputFetcher
import com.pancoku.adventofcode.command.AocCommand

fun main(args: Array<String>) {
    val client = AocClient(AocInputFetcher(2025))

    println("\n")
    println("=========================================")
    println("      Welcome to Advent of Code!         ")
    println("=========================================")
    println("Active year: ${client.year}")
    println("Type 'help' for available commands, or 'exit' to quit.")

    while (true) {
        print("> ")
        val rawInput = readlnOrNull() ?: break
        val command = AocCommand.create(rawInput)

        val shouldContinue = client.execute(command)
        if (!shouldContinue) break
    }
}
