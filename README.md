# 🎄 Advent of Code Kotlin Solver & CLI

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
./gradlew run
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
