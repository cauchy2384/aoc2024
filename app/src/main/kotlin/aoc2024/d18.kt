package aoc2024

import java.io.BufferedReader

class D18(val limit: Int, val bytes: Int): Solution<Long> {
    override val day = 18

    override fun part1(reader: BufferedReader): Long {
        val (m, n) = limit + 1 to limit + 1
        val mx = Matrix(m, n, '.')

        val start = 0 to 0
        val target = limit to limit

        reader.lineSequence()
            .take(bytes)
            .map{ it.split(",") }
            .map{ it[0].toInt() to it[1].toInt() }
            .forEach { 
                (x, y) -> mx.set(y, x, '#')
            }

        return bfs(mx, start, target)
    }

    fun bfs(mx: Matrix<Char>, start: Pair<Int, Int>, target: Pair<Int, Int>): Long {
        val queue = ArrayDeque<Triple<Int, Int, Long>>()
        queue.add(Triple(start.first, start.second, 0L))
        val visited = HashSet<Pair<Int, Int>>()

        while (queue.isNotEmpty()) {
            val (x, y, d) = queue.removeFirst()
            if (visited.contains(x to y)) {
                continue
            }
            visited.add(x to y)
            if (x == target.first && y == target.second) {
                return d
            }

            if (mx.get(x, y) != '.') {
                continue
            }

            for ((dx, dy) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
                val nx = x + dx
                val ny = y + dy

                queue.add(element = Triple(nx, ny, d + 1))
            }
        }

        return -1L
    }

    override fun part2(reader: BufferedReader): Long {
        val (m, n) = limit + 1 to limit + 1
        val mx = Matrix(m, n, '.')

        val start = 0 to 0
        val target = limit to limit

        reader.lineSequence()
            .map{ it.split(",") }
            .map{ it[0].toInt() to it[1].toInt() }
            .withIndex()
            .forEach {  
                (i, c) ->
                    val (x, y) = c
                    mx.set(y, x, '#')
                    val p = bfs(mx, start, target)
                    if (p == -1L) {
                        // println("$x,$y")
                        return i.toLong()
                    }
            }

        return -1L
    }
}
