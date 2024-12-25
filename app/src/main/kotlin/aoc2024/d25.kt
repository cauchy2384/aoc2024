package aoc2024

import java.io.BufferedReader

class D25: Solution<Long> {
    override val day = 25

    override fun part1(reader: BufferedReader): Long {
        val keys = HashSet<IntArray>()
        val locks = HashSet<IntArray>()

        reader.lineSequence()
            .chunked(8)
            .forEach{ 
                val seq = it.filter{ it != "" }
                val isLock = seq[0].all{ it == '#' }
                val shape = IntArray(5) {0}
                for (y in 1 .. 5) {
                    for (x in 0 until 5) {
                        if (seq[y][x] == '#') {
                            shape[x] += 1
                        }
                    }
                }
                if (isLock) {
                    locks.add(shape)
                } else {
                    keys.add(shape)
                }
            }

        var ans = 0L

        for (key in keys) {
            for(lock in locks) {
                val overlaps = (0 until 5)
                    .map{ lock[it] + key[it] }
                    .filter{ it > 5 }
                    .sum()
                if (overlaps == 0) {
                    ans += 1L
                } 
            }
        }

        return ans
    }

    override fun part2(reader: BufferedReader): Long {
        return -1L
    }
}
