package aoc2024

import java.io.BufferedReader

import kotlin.test.Test
import kotlin.test.assertEquals

data class Answers(
    val part1Example: Int?,
    val part1Input: Int?,
    val part2Example: Int?,
    val part2Input: Int?,
)

abstract class SolutionTest(
    val solution: Solution,
    val answers: Answers,
) {
    companion object {
        fun solve(filename: String, expected: Int?, solve: (BufferedReader) -> Int) {
            if (expected == null) {
                println("Test skipped")
                return
            }
            object {}.javaClass.getResourceAsStream(filename)?.use { stream ->
                stream.bufferedReader().use { reader ->
                    val actual = solve(reader)
                    println("File: $filename, Expected: $expected, Actual: $actual")
                    assertEquals(expected, actual, "Expected value: $expected but got: $actual") 
                }
            } ?: throw IllegalArgumentException("File not found: $filename")
        } 
    }

    fun resource(filename: String) = "/d%02d/%s".format(solution.day, filename)

    @Test fun example() {
        solve(resource("example.txt"), answers.part1Example, solution::part1)
    }

    @Test fun input() {
        solve(resource("input.txt"), answers.part1Input, solution::part1)
    }

    @Test fun example2() {
        solve(resource("example.txt"), answers.part2Example, solution::part2)
    }

    @Test fun input2() {
        solve(resource("input.txt"), answers.part2Input, solution::part2)
    }
}