package aoc2024

import java.io.BufferedReader
import java.math.BigInteger

class D11: Solution<Long> {
    override val day = 11

    override fun part1(reader: BufferedReader): Long {
        return solve(reader, 25)
    }

    override fun part2(reader: BufferedReader): Long {
        return solve(reader, 75)
    }

    fun solve(reader: BufferedReader, n: Int): Long {
        return reader.lineSequence().first()
            .split(" ")
            .map { it.toLong() }
            .map{ blink(it, n) }
            .sum()
    }

    val cache: MutableMap<Pair<Long, Int>, Long> = mutableMapOf()

    fun blink(v: Long, n: Int): Long {
        if (n == 0) {
            return 1
        }
        if (cache.containsKey(v to n)) {
            return cache[v to n]!!
        }

        var res = when {
            v == 0L -> blink(1L, n - 1)
            v.toString().length % 2 == 0 -> {
                val s = v.toString()
                val a = s.substring(0, s.length / 2)
                val b = s.substring(s.length / 2)
                return blink(a.toLong(), n - 1) + blink(b.toLong(), n - 1)
            }
            else -> blink(v * 2024L, n - 1)
        }

        cache[v to n] = res
        return res
    } 
}
