package com.pankoku.adventofcode.command

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class AocCommandTest {

    @Test
    fun testParseExitCommand() {
        assertEquals(AocCommand.Exit, AocCommand.create("exit"))
        assertEquals(AocCommand.Exit, AocCommand.create("quit"))
    }

    @Test
    fun testParseYearCommands() {
        assertEquals(AocCommand.ShowYear, AocCommand.create("year"))
        assertEquals(AocCommand.SetYear(2024), AocCommand.create("year 2024"))
    }

    @Test
    fun testParseInputCommands() {
        assertEquals(AocCommand.FetchInput(day = 5, year = null), AocCommand.create("input 5"))
        assertEquals(AocCommand.FetchInput(day = 5, year = 2023), AocCommand.create("fetch 5 2023"))
    }

    @Test
    fun testParseSolveCommands() {
        assertEquals(AocCommand.Solve(day = 1, part = null, year = null), AocCommand.create("solve 1"))
        assertEquals(AocCommand.Solve(day = 1, part = 2, year = null), AocCommand.create("solve 1 2"))
        assertEquals(AocCommand.Solve(day = 1, part = 2, year = 2024), AocCommand.create("solve 1 2 2024"))
    }

    @Test
    fun testParseUnknownCommand() {
        assertIs<AocCommand.Unknown>(AocCommand.create("foobar"))
    }
}
