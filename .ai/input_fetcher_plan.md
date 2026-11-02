# Advent of Code 2025 Input Fetcher Tool - Technical Plan

## Overview
This document outlines the design and implementation strategy for an automated Advent of Code input fetcher tool written in Kotlin. The tool downloads puzzle input directly from `adventofcode.com` using OkHttp and caches it locally to avoid unnecessary network requests.

---

## Advent of Code Etiquette & Best Practices
1. **Local Caching (Primary Rule):** Always check if `inputs/{year}/day{day}.txt` exists locally. If present, load directly from disk without hitting the network.
2. **Custom User-Agent:** Send a custom `User-Agent` header (`AdventOfCode-Kotlin-Fetcher/1.0 (+via github.com/pankoku/adventofcode)`) to identify requests responsibly per AoC guidelines.
3. **Session Cookie Management:** Securely load session cookie without risking accidental commits of private credentials.

---

## Component Architecture

### 1. `SessionProvider`
- **Location:** `adventofcode/src/main/kotlin/com/pankoku/adventofcode/client/SessionProvider.kt`
- **Responsibility:**
  - Reads session token from local `session.txt` file (at project root).
  - Falls back to `AOC_SESSION` environment variable if `session.txt` is missing or empty.
  - Throws descriptive `IllegalStateException` if neither source yields a valid token.

### 2. `InputCacheManager`
- **Location:** `adventofcode/src/main/kotlin/com/pankoku/adventofcode/client/InputCacheManager.kt`
- **Responsibility:**
  - Target Path: `.inputs/{year}/day{day_padded}.txt` (e.g., `.inputs/2025/day01.txt`).
  - `hasCache(year: Int, day: Int): Boolean`
  - `readCache(year: Int, day: Int): String`
  - `saveCache(year: Int, day: Int, content: String): File`

### 3. `AocInputFetcher`
- **Location:** `adventofcode/src/main/kotlin/com/pankoku/adventofcode/client/AocInputFetcher.kt`
- **Responsibility:**
  - Coordinates cache checking and HTTP fetching via OkHttp (`com.squareup.okhttp3.OkHttpClient`).
  - Endpoint: `https://adventofcode.com/{year}/day/{day}/input`
  - Headers:
    - `Cookie: session=<SESSION_TOKEN>`
    - `User-Agent: AdventOfCode-Kotlin-Fetcher/1.0`
  - Handles response status codes:
    - `200`: Success - caches input and returns content.
    - `400/403`: Invalid or expired session cookie.
    - `404`: Puzzle input not unlocked yet.
  - Methods:
    - `getInput(day: Int): String`
    - `getInputLines(day: Int): List<String>`

### 4. `.gitignore` Configuration
- Ensure sensitive credentials and downloaded inputs are not committed to git:
  - `session.txt`
  - `.env`
  - `.inputs/`

---

## Usage Example

```kotlin
val fetcher = AocInputFetcher(year = 2025)
val inputLines = fetcher.getInputLines(day = 1)
```
