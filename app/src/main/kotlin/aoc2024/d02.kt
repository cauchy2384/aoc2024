package aoc2024

import java.io.BufferedReader

import kotlin.math.abs
import kotlin.math.sign

class D02: Solution<Int> {
    override val day = 2

    override fun part1(reader: BufferedReader): Int {
        return reader.lineSequence()
            .filter { line -> isSafe(line.split(" ").map { it.toInt() })}
            .count()
    }

    fun isSafe(nums: List<Int>): Boolean {
        return  nums.zipWithNext()
            .map{ (a, b) -> (a - b).sign to (abs(a - b) in 1..3) }
            .zipWithNext()
            .all{ (a, b) -> (a.first == b.first) && a.second && b.second }
    }

    override fun part2(reader: BufferedReader): Int {
        return reader.lineSequence()
            .filter { line -> 
                val nums = line.split(" ").map { it.toInt() }
                (0 until nums.size).asSequence()
                    .map{ nums.filterIndexed { i, _ -> i != it } }
                    .any{ isSafe(it) }
            }
            .count()
    }
}
    