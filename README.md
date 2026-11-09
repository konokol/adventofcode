# 🎄 Advent of Code Kotlin Solver & CLI

[![Advent of Code](https://img.shields.io/badge/Advent%20of%20Code-2025-FFFF66?style=flat-square)](https://adventofcode.com)  

[![Language: Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg?style=flat-square)](LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/konokol/adventofcode?style=flat-square)](https://github.com/YOUR_GITHUB_USERNAME/adventofcode/stargazers)
[![GitHub last commit](https://img.shields.io/github/last-commit/konokol/adventofcode?style=flat-square)](https://github.com/YOUR_GITHUB_USERNAME/adventofcode)

A Kotlin runner for [Advent of Code](https://adventofcode.com/) puzzles with an interactive terminal CLI, automatic input downloader, local caching, and automatic solution discovery.

---

## ✨ Features

- **🕹️ Interactive CLI:** Manage active year, download inputs, and execute daily puzzle solutions directly from the terminal.
- **📥 Automatic Input Downloader:** Fetches puzzle inputs over the network and caches them locally in `.inputs/` to prevent redundant server requests.
- **🔌 Automatic Solution Discovery:** Automatically discovers new daily solutions using ServiceLoader and annotation processing.
- **⚡ On-Demand Execution:** Solvers are loaded lazily when requested by commands.

---

## 🚀 Getting Started

### 1. Configure Credentials
Create a `session.txt` file in the root project folder containing your Advent of Code session cookie:

```text
session=your_session_cookie_here
```

*(Note: `session.txt` and `.inputs/` are excluded in `.gitignore` to keep credentials and inputs private).*

### 2. Run the Application

```bash
./gradlew :adventofcode:run --console=plain
```
---

## 🕹️ CLI Commands

| Command | Description | Example |
| :--- | :--- | :--- |
| `year` | Displays the currently active year | `year` |
| `year <YYYY>` | Changes the active year | `year 2025` |
| `input <day>` | Fetches or loads cached input for specified day | `input 1` |
| `solve <day> [part]` | Solves the problem for day (Part 1, Part 2, or both) | `solve 1 2` |
| `help` | Displays the help menu | `help` |
| `exit` | Exits the application | `exit` |

---

## 🧪 Running Unit Tests

```bash
./gradlew test
```

---

## 📄 License

This project is licensed under the MIT License.
