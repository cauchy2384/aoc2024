package aoc2024

import java.io.BufferedReader

class D19: Solution<Long> {
    override val day = 19

    override fun part1(reader: BufferedReader): Long {
        val lines = reader.lineSequence().toList()

        val patterns = lines[0]
            .split(",")
            .map{ it.trim() }
            .toList()

        var isPossible: (String) -> Boolean
        isPossible = { s: String ->
            if (s == "") {
                true
            } else {
                patterns
                    .filter{ s.startsWith(it) }
                    .any{ isPossible(s.drop(it.length)) }
            }
        }.memoize()
            
        return lines.drop(2)
            .filter{ isPossible(it) }
            .count()
            .toLong()
    }

    override fun part2(reader: BufferedReader): Long {
        val lines = reader.lineSequence().toList()

        val patterns = lines[0]
            .split(",")
            .map{ it.trim() }
            .toList()

        var combinations: (String) -> Long
        combinations = { s: String ->
            if (s == "") {
                1L
            } else {
                patterns
                    .filter{ s.startsWith(it) }
                    .sumOf{ combinations(s.drop(it.length)) }
            }
        }.memoize()

        return lines.drop(2).sumOf{ combinations(it) }
    }
}
