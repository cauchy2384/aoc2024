package aoc2024

import java.io.BufferedReader
import java.util.PriorityQueue

class D20(val toSave: Long): Solution<Long> {
    override val day = 20

    val dirs = listOf(
        Pair(-1, 0),
        Pair(1, 0),
        Pair(0, 1),
        Pair(0, -1)
    )

    data class Coordinate(val y: Int, val x: Int)

    override fun part1(reader: BufferedReader): Long {
        val mx = Matrix(reader, { it })

        val (ys, xs, _) = mx.filter{ it.third == 'S' }.first()
        val (ye, xe, _) = mx.filter{ it.third == 'E' }.first()
        val start = Coordinate(ys, xs)
        val end = Coordinate(ye, xe)

        return paths(mx, start, end) 
    }

    fun paths(mx: Matrix<Char>, start: Coordinate, end: Coordinate): Long {
        val (best, path) = shortest(mx, start, end)

        val pathMap = HashMap<Coordinate, Long>()
        for ((i, c) in path.withIndex()) {
            pathMap[c] = best - i.toLong()
        }
        pathMap[end] = 0

        val q = ArrayDeque<Pair<Coordinate, Long>>()
        for ((i, c) in path.withIndex()) {
            q.add(c to i.toLong())
        }

        val usedCheats = mutableSetOf<Coordinate>()
        val saved = mutableMapOf<Long, Int>()

        var ans = 0L
        while (q.isNotEmpty()) {
            var (c, p) = q.removeFirst()

            for (dir in dirs) {
                val ny = c.y + dir.first
                val nx = c.x + dir.second
                if (mx.get(ny, nx) != '#') {
                    continue
                }
                if (usedCheats.contains(Coordinate(ny, nx))) {
                    continue
                }
                usedCheats.add(Coordinate(ny, nx))
                for (dir2 in dirs) {
                    val ny2 = ny + dir2.first
                    val nx2 = nx + dir2.second
                    if (mx.get(ny2, nx2) != '.' && mx.get(ny2, nx2) != 'E') {
                        continue
                    }
                    if (!pathMap.containsKey(Coordinate(ny2, nx2))) {
                        continue
                    }
                    val np = p + 2 + pathMap[Coordinate(ny2, nx2)]!!
                    val savedWithCheat = best - np
                    if (savedWithCheat >= toSave) {
                        saved[savedWithCheat] = saved.getOrDefault(savedWithCheat, 0) + 1
                        ans += 1L
                    }
                }
            } 
        }

        return saved.values.sum().toLong()
    }

    fun shortest(mx: Matrix<Char>, start: Coordinate, end: Coordinate): Pair<Long, List<Coordinate>> {
        val heap = PriorityQueue<Triple<Coordinate, Long, MutableList<Coordinate>>>(compareBy {it.second})
        heap.add(Triple(start, 0L, mutableListOf()))

        val visited = mutableSetOf<Coordinate>()

        while (heap.isNotEmpty()) {
            var (c, d, path) = heap.poll()
            if (visited.contains(c)) {
                continue
            }
            path = path.toMutableList()
            path.add(c)
            visited.add(c)

            if (c.y == end.y && c.x == end.x) {
                return d to path
            }

            if (mx.get(c.y, c.x) == null) {
                continue
            } 
            if (mx.get(c.y, c.x) == '#') {
                continue
            }

            for (dir in dirs) {
                val ny = c.y + dir.first
                val nx = c.x + dir.second
                if (mx.get(ny, nx) != '#' && !visited.contains(Coordinate(ny, nx))) {
                    heap.add(Triple(Coordinate(ny, nx), d + 1, path))
                }    
            }  
        }

        return -1L to listOf()
    }

    override fun part2(reader: BufferedReader): Long {
        val mx = Matrix(reader, { it })

        val (ys, xs, _) = mx.filter{ it.third == 'S' }.first()
        val (ye, xe, _) = mx.filter{ it.third == 'E' }.first()
        val start = Coordinate(ys, xs)
        val end = Coordinate(ye, xe)

        return paths2(mx, start, end) 
    }

    fun paths2(mx: Matrix<Char>, start: Coordinate, end: Coordinate): Long {
        val (best, path) = shortest(mx, start, end)

        val pathMap = HashMap<Coordinate, Long>()
        for ((i, c) in path.withIndex()) {
            pathMap[c] = best - i.toLong()
        }
        pathMap[end] = 0

        val q = ArrayDeque<Pair<Coordinate, Long>>()
        for ((i, c) in path.withIndex()) {
            q.add(c to i.toLong())
        }

        var ans = 0L
        while (q.isNotEmpty()) {
            var (c, p) = q.removeFirst()

            val nexts = HashMap<Coordinate, Long>()
            var opts = mutableListOf<Coordinate>()
            opts.add(c)

            for (skip in 0 .. 20) {
                val opts2 = mutableListOf<Coordinate>()
                while (opts.isNotEmpty()) {
                    val c = opts.removeFirst()
                    if (nexts.containsKey(c)) {
                        continue
                    }
                    nexts[c] = skip.toLong()

                    for (dir in dirs) {
                        val ny2 = c.y + dir.first
                        val nx2 = c.x + dir.second
                        opts2.add(Coordinate(ny2, nx2))
                    }
                }
                opts = opts2
            }

            for (next in nexts.keys) {
                val (ny2, nx2) = next
                if (mx.get(ny2, nx2) != '.' && mx.get(ny2, nx2) != 'E') {
                    continue
                }
                if (!pathMap.containsKey(Coordinate(ny2, nx2))) {
                    continue
                }
                val np = p + nexts[next]!! + pathMap[Coordinate(ny2, nx2)]!!
                val savedWithCheat = best - np
                if (savedWithCheat >= toSave) {
                    ans += 1L
                }
            } 
        }

        return ans
    }    
}
