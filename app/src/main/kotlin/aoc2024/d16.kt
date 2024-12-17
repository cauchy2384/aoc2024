package aoc2024

import java.io.BufferedReader
import java.util.PriorityQueue
import kotlin.math.min

class D16: Solution<Long> {
    override val day = 16

    val dirs = HashMap<Char, Pair<Int, Int>>()

    init {
        dirs['^'] = -1 to 0
        dirs['v'] = 1 to 0
        dirs['>'] = 0 to 1
        dirs['<'] = 0 to -1
    }

    data class Coordinate(val y: Int, val x: Int, val dir: Char)

    override fun part1(reader: BufferedReader): Long {
        val mx = Matrix(reader, { it })

        val (ys, xs, _) = mx.filter{ it.third == 'S' }.first()
        val (ye, xe, _) = mx.filter{ it.third == 'E' }.first()

        val heap = PriorityQueue<Pair<Coordinate, Long>>(compareBy {it.second})
        heap.add(Coordinate(ys, xs, '>') to 0L)

        val visited = mutableSetOf<Coordinate>()

        while (heap.isNotEmpty()) {
            val (c, d) = heap.poll()
            if (visited.contains(c)) {
                continue
            }
            visited.add(c)

            if (c.y == ye && c.x == xe) {
                return d
            }

            if (mx.get(c.y, c.x) == '#') {
                continue
            }

            when (c.dir) {
                '^' -> {
                    heap.add(Coordinate(c.y - 1, c.x, '^') to d + 1L)
                    heap.add(Coordinate(c.y, c.x, '<') to d + 1000L)
                    heap.add(Coordinate(c.y, c.x, '>') to d + 1000L)
                }
                'v' -> {
                    heap.add(Coordinate(c.y + 1, c.x, 'v') to d + 1L)
                    heap.add(Coordinate(c.y, c.x, '<') to d + 1000L)
                    heap.add(Coordinate(c.y, c.x, '>') to d + 1000L)
                }
                '>' -> {
                    heap.add(Coordinate(c.y, c.x + 1, '>') to d + 1L)
                    heap.add(Coordinate(c.y, c.x, '^') to d + 1000L)
                    heap.add(Coordinate(c.y, c.x, 'v') to d + 1000L)
                }
                '<' -> {
                    heap.add(Coordinate(c.y, c.x - 1, '<') to d + 1L)
                    heap.add(Coordinate(c.y, c.x, '^') to d + 1000L)
                    heap.add(Coordinate(c.y, c.x, 'v') to d + 1000L)
                }
                else -> {}   
            }                
        }

        return -1L
    }

    override fun part2(reader: BufferedReader): Long {
        val mx = Matrix(reader, { it })

        val (ys, xs, _) = mx.filter{ it.third == 'S' }.first()
        val (ye, xe, _) = mx.filter{ it.third == 'E' }.first()

        val heap = PriorityQueue<Triple<Coordinate, Long, List<Coordinate>>>(compareBy {it.second})
        heap.add(Triple(Coordinate(ys, xs, '>'), 0L, mutableListOf()))

        val visited = HashMap<Coordinate, Long>()

        var best = Long.MAX_VALUE

        val cells = HashSet<Pair<Int, Int>>()

        while (heap.isNotEmpty()) {
            val (c, d, p) = heap.poll()
            if (d > best) {
                continue
            }
            if (visited.containsKey(c) && visited[c]!! < d) {
                continue
            }
            visited[c] = d
            val path = p + c

            if (c.y == ye && c.x == xe) {
                best = min(best, d)
                if (d == best) {
                    for (cell in path) {
                        val (y, x, _) = cell
                        cells.add(y to x)
                    }
                }
                continue
            }

            if (mx.get(c.y, c.x) == '#') {
                continue
            }

            when (c.dir) {
                '^' -> {
                    heap.add(Triple(Coordinate(c.y - 1, c.x, '^'), d + 1L, path))
                    heap.add(Triple(Coordinate(c.y, c.x, '<'), d + 1000L, path))
                    heap.add(Triple(Coordinate(c.y, c.x, '>'), d + 1000L, path))
                }
                'v' -> {
                    heap.add(Triple(Coordinate(c.y + 1, c.x, 'v'), d + 1L, path))
                    heap.add(Triple(Coordinate(c.y, c.x, '<'), d + 1000L, path))
                    heap.add(Triple(Coordinate(c.y, c.x, '>'), d + 1000L, path))
                }
                '>' -> {
                    heap.add(Triple(Coordinate(c.y, c.x + 1, '>'), d + 1L, path))
                    heap.add(Triple(Coordinate(c.y, c.x, '^'), d + 1000L, path))
                    heap.add(Triple(Coordinate(c.y, c.x, 'v'), d + 1000L, path))
                }
                '<' -> {
                    heap.add(Triple(Coordinate(c.y, c.x - 1, '<'), d + 1L, path))
                    heap.add(Triple(Coordinate(c.y, c.x, '^'), d + 1000L, path))
                    heap.add(Triple(Coordinate(c.y, c.x, 'v'), d + 1000L, path))
                }
                else -> {}   
            }                
        }

        cells.forEach{ (y, x) -> mx.set(y, x, '0') }

        return cells.size.toLong()
    }
}
