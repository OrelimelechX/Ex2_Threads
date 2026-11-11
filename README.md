# Multithreaded Subset Sum — Exercise 2

## Project Overview

This Java project searches for a subset of integers whose sum equals a target value `b`. It uses recursive search and two worker threads.

The original algorithm and starter code were supplied by the course instructor. The exercise focuses on refactoring with a local AI assistant, Javadoc documentation, Git collaboration, and merge conflict resolution.

## Project Structure

- `src/SharedData.java` — Stores the input numbers, target sum, shared solution flag, and solution selection array.
- `src/ThreadCheckArray.java` — Implements the recursive search performed by each worker thread.
- `src/TestThreadCheckArray.java` — Reads user input, starts both threads, waits for them to finish, and prints the result.

## Algorithm and Thread Synchronization

For inputs containing more than one element, the program divides the search between two threads:

- `thread1` searches subsets that include the final input element.
- `thread2` searches subsets that exclude the final input element.

For the remaining elements, the recursive method explores two possibilities: including the current element or excluding it.

When `n == 1`, the recursion finds a solution if the remaining target is `0` or equals the first element. The first element is marked as selected when it equals the remaining target.

Each worker maintains its own `boolean[] winArray` to record which elements belong to its solution. Shared state is accessed through `synchronized (sd)` blocks. Workers check a shared flag to stop searching after a solution is found, and a successful worker publishes its selection array.

The main thread uses `join()` to wait for both workers before displaying the result.

## Data Structure Refactoring

The exercise replaces the numeric `int[] array` with `ArrayList<Integer>` while preserving the existing search logic.

The changes include:

- Importing `java.util.ArrayList`.
- Replacing numeric array access with `array.get(index)`.
- Replacing numeric array length checks with `array.size()`.
- Preserving solution selection arrays as primitive `boolean[]`.
- Initializing selection arrays using `new boolean[array.size()]`.

Using `ArrayList<Integer>` changes how the numbers are stored and accessed; it does not change the underlying recursive search algorithm.

## Current Refactoring Status

Student A has refactored `SharedData.java` and `ThreadCheckArray.java`.

The current version of `TestThreadCheckArray.java` still uses `int[]` and requires Student B's changes. Until these changes are integrated, the project contains incompatible types and is not ready for a full compilation and execution check.

After merging, the team must verify compilation, program output, and Javadoc generation. This status section should then be updated to reflect the actual results.

## Javadoc Documentation

Javadoc has been added to `ThreadCheckArray.java` and to methods in `SharedData.java`. Documentation of the remaining code must be reviewed after integration.

Generic types inside Javadoc are written using inline code tags, for example:

```java
/**
 * Stores the input numbers in an {@code ArrayList<Integer>}.
 */
```

This avoids interpreting generic type brackets as HTML tags.

After integration, generate HTML documentation using IntelliJ IDEA:

1. Open **Tools → Generate JavaDoc**.
2. Select the whole project.
3. Set the output directory to `docs/`.
4. Generate the documentation and inspect `docs/index.html`.

## Authors and Git Collaboration Roles

### Student A — Or Elimelech

- Creates the local Git repository and GitHub repository.
- Invites Student B as a collaborator.
- Refactors `SharedData.java` and `ThreadCheckArray.java` on `main`.
- Generates and reviews `README.md` using the local AI assistant.
- Commits and pushes the changes.
- Pulls the merged result and verifies it locally.

### Student B — Eyal Hirsch

- Clones the shared repository.
- Creates `feature/arraylist-refactor-b`.
- Writes an independent refactoring prompt.
- Refactors `TestThreadCheckArray.java` and `ThreadCheckArray.java`.
- Commits and pushes the feature branch.
- Updates local `main` with Student A's changes.
- Merges the feature branch into `main` and resolves conflicts using IntelliJ IDEA's **3-Way Merge** tool.
- Verifies and pushes the merged result.

Both students edit `ThreadCheckArray.java` to practice resolving conflicting changes. Both review the final Git history, code ownership, documentation, and program output.

These responsibilities describe the planned workflow; completed work is summarized in the Current Refactoring Status section.

## Local AI Assistance

The exercise uses Continue in IntelliJ IDEA with the local `Qwen2.5-Coder-7B-Instruct` model served by LM Studio.

The AI assists with refactoring and documentation. Generated output is reviewed manually for correctness, preservation of the original logic, and compliance with the exercise requirements.

## Verification After Integration

Run `TestThreadCheckArray.java` with:

```text
Array size: 4
Numbers: 1 2 3 7
Target: 5
```

Expected solution:

```text
Solution for b : 5,n = 4
I:    0    1    2    3
A:    1    2    3    7
C:    0    1    1    0
```

The selection vector identifies `2` and `3`, whose sum is `5`.

This is the expected result for the required smoke test, not a record of a completed test. Both students must run and verify the merged project.

## Git Ignore Rules

The project's `.gitignore` excludes generated files and local IDE configuration, including:

```gitignore
out/
.idea/
*.iml
docs/
*.class
```

Although `docs/` is excluded from Git, the generated documentation and the hidden `.git` directory must be included in the final submission archive as required by the exercise.

## Licensing

This course project is subject to the **Braude College Academic License** specified in the exercise materials. Refer to those materials for the applicable restrictions.