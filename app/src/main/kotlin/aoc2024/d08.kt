package aoc2024

import java.io.BufferedReader

class D08: Solution<Long> {
    override val day = 8

    override fun part1(reader: BufferedReader): Long {
        val mx = Matrix<Char>(reader, { it })

        val antennas = HashMap<Char, MutableList<Pair<Int, Int>>>()

        for (i in 0 until mx.m) {
            for (j in 0 until mx.n) {
                when (mx.get(i, j)) {
                    '.' -> {}
                    else -> antennas.getOrPut(mx.get(i, j)!!) { mutableListOf() }.add(i to j)
                }
            }
        }
        val antinodes = HashSet<Pair<Int, Int>>()

        var ans = 0L
        for (key in antennas.keys) {
            if (antennas[key]!!.size < 2) {
                continue
            }
            for (i in 0 until antennas[key]!!.size) {
                val (x, y) = antennas[key]!![i]
                for (j in i + 1 until antennas[key]!!.size) {
                    val (x2, y2) = antennas[key]!![j]
                    // println("($x, $y) ($x2, $y2)")
                    val (dx, dy) = x2 - x to y2 - y
                    val (xa, ya) = x - dx to y - dy
                    if (xa in 0 until mx.m && ya in 0 until mx.n) {
                        mx.set(xa, ya, '#')
                        antinodes.add(xa to ya)
                        ans += 1L
                    }
                    val (xb, yb) = x2 + dx to y2 + dy
                    if (xb in 0 until mx.m && yb in 0 until mx.n) {
                        mx.set(xb, yb, '#')
                        antinodes.add(xb to yb)
                        ans += 1L
                    }
                }
            }
        }

        return antinodes.size.toLong() 
    }

    override fun part2(reader: BufferedReader): Long {
        val mx = Matrix<Char>(reader, { it })

        val antennas = HashMap<Char, MutableList<Pair<Int, Int>>>()

        for (i in 0 until mx.m) {
            for (j in 0 until mx.n) {
                when (mx.get(i, j)) {
                    '.' -> {}
                    else -> antennas.getOrPut(mx.get(i, j)!!) { mutableListOf() }.add(i to j)
                }
            }
        }

        val antinodes = HashSet<Pair<Int, Int>>()

        for (key in antennas.keys) {
            if (antennas[key]!!.size < 2) {
                continue
            }
            for (i in 0 until antennas[key]!!.size) {
                val (x, y) = antennas[key]!![i]
                for (j in i + 1 until antennas[key]!!.size) {
                    val (x2, y2) = antennas[key]!![j]
                    val (dx, dy) = x2 - x to y2 - y
                    var (xa, ya) = x to y
                    antinodes.add(xa to ya)
                    mx.set(xa, ya, '#')
                    while (xa in 0 until mx.m && ya in 0 until mx.n) {
                        xa = xa + dx
                        ya = ya + dy
                        if (xa in 0 until mx.m && ya in 0 until mx.n) {
                            mx.set(xa, ya, '#')
                            antinodes.add(xa to ya)
                        }
                    }
                    xa = x 
                    ya = y
                    while (xa in 0 until mx.m && ya in 0 until mx.n) {
                        xa = xa - dx
                        ya = ya - dy
                        if (xa in 0 until mx.m && ya in 0 until mx.n) {
                            mx.set(xa, ya, '#')
                            antinodes.add(xa to ya)
                        }
                    }
                }
            }
        }

        return antinodes.size.toLong() 
    }
}
