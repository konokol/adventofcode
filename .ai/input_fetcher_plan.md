# Advent of Code Client & Command Architecture Plan

## Overview
This document outlines the expanded architecture for the Advent of Code Kotlin tool. Terminal input strings are parsed into executable `AocCommand` domain objects via `AocCommand.Factory` (Factory pattern) and executed by `AocClient`. `AocClient` coordinates puzzle input fetching via `AocInputFetcher` and problem solving via `SolverRegistry`, which lazily loads `DaySolver` instances using `ServiceLoader` on-demand per command execution. `META-INF/services/` entries are generated automatically at build time using Google `@AutoService` and `kapt`.

---

## 🏗️ System Architecture & Data Flow

```
[ Terminal Input String ]
           │
           ▼
 [ AocCommand.Factory ] ─────► Creates typed [ AocCommand ]
           │
           ▼
     [ AocClient ] ─────► Executes Command
     ├── AocInputFetcher (Fetches / Loads cached puzzle inputs)
     └── SolverRegistry  (Lazy On-Command ServiceLoader lookup & caching)
```

---

## 🧩 Component Specifications

### 1. `AocCommand` & `AocCommand.Factory` (Domain Model & Factory)
- Location: `adventofcode/src/main/kotlin/com/pankoku/adventofcode/command/AocCommand.kt`
- Sealed interface representing executable commands created by `AocCommand.Factory`:

```kotlin
sealed interface AocCommand {
    data class SetYear(val year: Int) : AocCommand
    data class FetchInput(val day: Int, val year: Int? = null) : AocCommand
    data class Solve(val day: Int, val part: Int? = null, val year: Int? = null) : AocCommand
    data object ShowYear : AocCommand
    data object Help : AocCommand
    data object Exit : AocCommand
    data class Unknown(val rawInput: String, val error: String) : AocCommand

    companion object Factory {
        fun create(rawInput: String): AocCommand
    }
}
```

Supported Syntax:
- `year [y]` — Shows or sets current year (e.g., `year 2024` or `year`).
- `input <day>` / `fetch <day>` — Fetches or loads puzzle input for specified day.
- `solve <day> [part]` — Solves puzzle for day (part 1, part 2, or both).
- `help` — Displays command menu.
- `exit` / `quit` — Exits the application.

### 2. `DaySolver` & `SolverRegistry` (ServiceLoader On-Command-Loading)
- Location: `adventofcode/src/main/kotlin/com/pankoku/adventofcode/solver/DaySolver.kt`
- Interface for day solutions:
  ```kotlin
  interface DaySolver {
      val year: Int
      val day: Int
      fun solvePart1(input: String): Any
      fun solvePart2(input: String): Any
  }
  ```
- Location: `adventofcode/src/main/kotlin/com/pankoku/adventofcode/solver/SolverRegistry.kt`
- **Lazy ServiceLoader On-Command Loading:** Solvers are not eagerly loaded on app startup. When `getSolver(year, day)` is invoked, `SolverRegistry` checks an in-memory cache. If not found, it triggers `ServiceLoader.load(DaySolver::class.java)` on-demand, filters for the matching `(year, day)` solver, caches it, and returns it.

### 3. Automatic SPI Generation via Google `@AutoService`
- **Gradle Config:** Enable `kotlin-kapt` plugin and add dependencies `compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")` and `kapt("com.google.auto.service:auto-service:1.1.1")`.
- **Automatic Code Generation:** Any solution class annotated with `@AutoService(DaySolver::class)` automatically gets registered in `META-INF/services/com.pankoku.adventofcode.solver.DaySolver` during `gradle build` compilation.

```kotlin
@AutoService(DaySolver::class)
class Day01Solver : DaySolver {
    override val year: Int = 2026
    override val day: Int = 1
    ...
}
```

### 4. `AocClient`
- Location: `adventofcode/src/main/kotlin/com/pankoku/adventofcode/client/AocClient.kt`
- Central execution engine holding state (`currentYear`, `inputFetcher`, `solverRegistry`).
- Method: `fun execute(command: AocCommand): Boolean`
  - Returns `false` on `Exit` to terminate the loop.
  - Handles `FetchInput` by invoking `inputFetcher.getInput(day, year)`.
  - Handles `Solve` by fetching input, finding `DaySolver` via `SolverRegistry`, executing solution, and logging execution time.

### 5. `App.kt` REPL Terminal Loop
- Location: `adventofcode/src/main/kotlin/com/pankoku/adventofcode/App.kt`
- Clean terminal loop reading input string, passing it to `AocCommand.create(rawInput)`, and executing via `aocClient.execute(command)`.

---

## 🔒 Etiquette & Caching Reminders
- Puzzle inputs are cached in `.inputs/{year}/day{day_padded}.txt`.
- `.inputs/`, `session.txt`, and `.env` remain in `.gitignore`.
