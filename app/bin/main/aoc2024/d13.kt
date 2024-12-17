package aoc2024

import java.io.BufferedReader
import kotlin.math.min
import java.math.BigInteger

class D13: Solution<Long> {
    override val day = 13

    override fun part1(reader: BufferedReader): Long {
        return go(reader, 0L)
    }

    override fun part2(reader: BufferedReader): Long {
        return go(reader, 10000000000000L)
    }

    fun go(reader: BufferedReader, delta: Long): Long {
        return reader.lineSequence()
            .chunked(4)
            .map{ it.map{ it.split(" ").map{ it.split(", ") } } }
            .map{
                listOf(
                    it[0][2][0] to it[0][3][0],
                    it[1][2][0] to it[1][3][0],
                    it[2][1][0] to it[2][2][0],
                )
                    .map{ (x, y) -> x.filter{ it.isDigit() } to y.filter{ it.isDigit() } }
                    .map{ (x, y) -> x.toLong() to y.toLong() }
            }
            .map{ solve(it[0], it[1], it[2].first + delta to it[2].second + delta ) }
            .sum()
    }

    fun solve(a: Pair<Long,Long>, b: Pair<Long,Long>, target: Pair<Long, Long>): Long {
        val a1 = a.first
        val a2 = a.second
        val b1 = b.first
        val b2 = b.second
        val c1 = target.first
        val c2 = target.second
    
        val y = (c1 * a2 - c2 * a1) / (a2 * b1 - a1 * b2)
        val x = (c1 - b1 * y) / a1

        val e1 = a1 * x + b1 * y
        val e2 = a2 * x + b2 * y
        if (e1 == c1 && e2 == c2) {
            return 3L * x.toLong() + 1L * y.toLong()
        }

        return 0L 
    }
}
