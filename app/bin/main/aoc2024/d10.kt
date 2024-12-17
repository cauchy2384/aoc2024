package aoc2024

import java.io.BufferedReader
import kotlin.doubleArrayOf

class D10: Solution<Long> {
    override val day = 10

    override fun part1(reader: BufferedReader): Long {
        val mx = Matrix(reader, { it.code - '0'.code })

        return mx.filter { (_, _, v) -> v == 0 }
            .map{ score(mx, it.first, it.second) }
            .sum()
    }

    override fun part2(reader: BufferedReader): Long {
        val mx = Matrix(reader, { it.code - '0'.code })

        return mx.filter { (_, _, v) -> v == 0 }
            .map{ score(mx, it.first, it.second, null) }
            .sum()
    }

    val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    fun score(mx: Matrix<Int>, x: Int, y: Int, visited: HashSet<Pair<Int, Int>>? = HashSet()): Long {
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(x to y)

        tailrec fun rec(queue: ArrayDeque<Pair<Int, Int>>, visited: HashSet<Pair<Int, Int>>?, score: Long = 0L): Long {
            if (queue.isEmpty()) {
                return score
            }

            val (x, y) = queue.removeFirst()
            if (visited != null) {
                if (visited.contains(x to y)) {
                    return rec(queue, visited, score)
                }
                visited.add(x to y)
            } 

            if (mx.get(x, y) == 9) {
                return rec(queue, visited, score + 1)
            }

            val v = mx.get(x, y)
            queue.addAll(
                directions
                    .map { (dx, dy) -> x + dx to y + dy }
                    .filter { v!! + 1 == mx.get(it.first, it.second) }
            )

            return rec(queue, visited, score)
        }

        return rec(queue, visited)
    }
}
