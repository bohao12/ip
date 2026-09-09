---
name: seedu-java-coding-standard
description: Enforces the SE-EDU Java Coding Standard (basic + intermediate rules) covering naming conventions, layout, statement structures, imports, and Javadoc formatting for Java code.
---

# SE-EDU Java Coding Standard (Basic + Intermediate)

This skill provides guidelines and checks for enforcing the SE-EDU Java Coding Standard (basic + intermediate rules) in Java code. For topics not explicitly covered here, default to the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).

## 1. Naming Conventions

- **Packages**: All lowercase letters (e.g., `yao.ui`, `yao.task`). Do not use `edu.nus.comp.*`.
- **Classes / Enums**: Nouns in `PascalCase` (e.g., `Task`, `Deadline`, `Event`).
- **Methods**: Verbs in `camelCase` (e.g., `markAsDone()`, `getTaskSummary()`).
  - Unit test methods may use `feature_scenario_behavior()` (e.g., `markAsDone_validIndex_success()`).
- **Variables**: `camelCase` (e.g., `taskCount`, `isDone`).
  - Short scope / index variables can be `i`, `j`, `k`, `c`, `d`.
  - Large scope variables must have clear, descriptive long names.
- **Constants**: `SCREAMING_SNAKE_CASE` (e.g., `MAX_TASKS`, `FILE_PATH`). Associated constants share a common prefix.
- **Booleans**: Name boolean variables and methods to sound like booleans using prefixes (`is`, `has`, `can`, `should`, `was`).
  - Example: `boolean isDone;`, `boolean hasLicense()`.
  - Setter format: `void setDone(boolean isDone)`.
- **Acronyms**: Do not uppercase acronyms in names (e.g., `exportHtmlSource()` instead of `exportHTMLSource()`, `dvdPlayer` instead of `DVDPlayer`).
- **Collections**: Plural form for names representing a collection or array (e.g., `tasks`, `values`).
- **Language**: All names must be written in English.

## 2. Layout & Formatting

- **Indentation**: 4 spaces (no tabs).
- **Line Length**: Soft limit 110 characters, hard limit 120 characters.
  - Continuation / wrapped line indentation: 8 spaces (twice normal indentation).
- **Line Breaks**:
  - Break after commas or before operators (`+`, `.`, `&&`, etc.).
  - Method name stays attached to opening parenthesis `(`.
  - Prefer higher-level breaks outside parenthesized expressions.
- **Brackets**: K&R / Egyptian style (opening brace `{` on the same line).
- **Whitespace**:
  - Surround binary/ternary operators with spaces (`a = (b + c) * d`).
  - Follow Java reserved words with space (`while (true) {`, `if (condition) {`).
  - Follow commas and semicolons in `for` loops with a space.
  - Blank line between logical units within a block.

## 3. Statements & Imports

- **Packages & Imports**:
  - Every class must belong to a package.
  - Explicit imports only (no wildcard imports like `import java.util.*;`).
  - Consistent import ordering (grouped by static imports, standard library, third-party, local packages).
- **Conditionals & Loops**:
  - Always wrap body of loops (`for`, `while`, `do-while`) and conditionals (`if`, `else`) in curly braces `{ }`, even for single statements.
  - Put conditional statements on a separate line.
  - In `switch` statements, `case` fallthrough without a `break` requires an explicit `// Fallthrough` comment.
- **Variables**: Initialize variables where declared in the smallest possible scope.

## 4. Comments & Javadoc

- **Language**: American English only.
- **Header Comments**: Write Javadoc header comments for all classes and public methods (can omit for simple getters/setters or `@Override` methods where parent Javadoc applies as-is).
- **Javadoc Format**:
  - Opening `/**` on a separate line.
  - First sentence is a summary starting with 3rd-person singular present verb (e.g., `Returns ...`, `Adds ...`, `Executes ...`).
  - Blank line before `@param`, `@return`, `@throws` block.
  - Parameter description ends with punctuation.
  - No blank line between Javadoc block and the class/method declaration.
