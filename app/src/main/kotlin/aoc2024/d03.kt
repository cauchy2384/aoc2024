package aoc2024

import java.io.BufferedReader

class D03: Solution {
    override val day = 3

    override fun part1(reader: BufferedReader): Int {
        val re = "mul\\((\\d+),(\\d+)\\)".toRegex()
        return reader.lineSequence()
            .sumOf { 
                re.findAll(it)
                .map { it.destructured }
                .sumOf { (a, b) -> a.toInt() * b.toInt() }
            }
    }

    override fun part2(reader: BufferedReader): Int {
        val re = "mul\\((\\d+),(\\d+)\\)|(do\\(\\))|(don't\\(\\))".toRegex()
        return reader.lineSequence()
            .fold(true to 0) { (ok, sum), line ->
                re.findAll(line)
                .fold(ok to sum) { (ok, sum), match ->
                    when (match.value) {
                        "do()" -> true to sum
                        "don't()" -> false to sum
                        else -> {
                            if (ok) {
                                val (a, b) = match.destructured
                                ok to sum + a.toInt() * b.toInt()
                            } else {
                                ok to sum
                            }
                        }
                    }
                }
            }.second
    }
}
