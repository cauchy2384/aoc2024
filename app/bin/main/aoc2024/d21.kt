package aoc2024

import java.io.BufferedReader
import java.util.PriorityQueue

import kotlin.math.abs
import kotlin.math.min

class D21(val last: Int): Solution<Long> {
    override val day = 21

    override fun part1(reader: BufferedReader): Long {
        var ans = reader.lineSequence()
            .map{ 
                it to score(it, last, Long.MAX_VALUE)
             }
            .map{ (_a, _b) -> 
                val a = _a.filter { it.isDigit() }.toLong()
                val b = _b
                a * b
            }
            .sum() 

        return ans
    }

    override fun part2(reader: BufferedReader): Long {
        var ans = reader.lineSequence()
            .map{ it to score(it, last, Long.MAX_VALUE) }
            .map{ (_a, _b) -> 
                val a = _a.filter { it.isDigit() }.toLong()
                val b = _b
                a * b
            }
            .sum() 

        return ans
    }

    var numeric: NumericKeypad = NumericKeypad()
    var directional: DirectionalKeypad = DirectionalKeypad()

    val cache = mutableMapOf<Pair<String, Int>, Long>()

    fun score(input: String, level: Int, best: Long): Long {
        if (cache.containsKey(input to level)) {
            return cache[input to level]!!
        }

        var ans = 0L

        when (level) {
            0 -> {
                val (_y, _x) = directional.getc('A')
                directional.y = _y
                directional.x = _x
        
                return input.map{ c ->
                    val v = directional.opts(c).map{ it.length }.min()        
                    val (y, x) = directional.getc(c)
                    directional.x = x
                    directional.y = y
                    v.toLong()
                }.sum()
            }
            last -> {
                ans = Long.MAX_VALUE
                for(s in numeric.typeMany(input)) {
                    ans = min(score(s, level - 1, ans), ans) 
                }
            }
            else -> {
                val (_y, _x) = directional.getc('A')
                directional.y = _y
                directional.x = _x
        
                for (c in input) {
                    ans += directional.opts(c).map{ score(it, level - 1, best) }.min()        
                    if (ans > best) {
                        return Long.MAX_VALUE
                    }
                    val (y, x) = directional.getc(c)
                    directional.x = x
                    directional.y = y
                }
            }
        }

        cache[input to level] = ans

        return ans
    }
}

open class Keypad {
    var rows: List<List<Char>> = listOf()
    var x: Int = 0
    var y: Int = 0

    fun typeMany(input: String): List<String> {
        var ans = mutableListOf<String>("")

        val (_y, _x) = getc('A')
        this.y = _y
        this.x = _x

        input.forEach{ c ->
            var ans2 = mutableListOf<String>()
           
            opts(c).forEach{
                ans.forEach{ a ->
                    ans2.add(a + it)
                }
            }            
            ans = ans2

            val (y, x) = getc(c)
            this.x = x
            this.y = y
        }

        return ans
    }

    data class El(
        val y: Int,
        val x: Int,
        val p: String,
        val visited: HashSet<Pair<Int, Int>>,
    )

    val cacheOpts = mutableMapOf<Triple<Int, Int, Char>, List<String>>()

    fun opts(c: Char): List<String> {
        val key = Triple(y, x, c)
        if (cacheOpts.containsKey(key)) {
            return cacheOpts[key]!!
        }

        var res = mutableListOf<String>()

        var q = ArrayDeque<El>()
        q.add(El(y, x, "", HashSet()))

        var best = 1000000000

        while (q.isNotEmpty()) {
            val el = q.removeFirst()
            val (y, x, path, visited) = el

            if (x < 0 || x >= rows[0].size || y < 0 || y >= rows.size) {
                continue
            }
            if (path.length > best) {
                continue
            }
            if (rows[y][x] == '#') {
                continue
            }
            if (rows[y][x] == c) {
                if (path.length < best) {
                    best = path.length
                    res = mutableListOf()
                }
                res.add(path + 'A')
            }

            for (dir in directions.keys) {
                val (dy, dx) = directions[dir]!!
                val ny = y + dy
                val nx = x + dx
                if (visited.contains(ny to nx)) {
                    continue
                }
                @Suppress("UNCHECKED_CAST")
                val vst = visited.clone() as HashSet<Pair<Int, Int>>
                vst.add(ny to nx)
                q.add(El(ny, nx, path + dir, vst))
            }
        }

        cacheOpts[key] = res
        return res
    }

    val directions = mapOf(
        '^' to (-1 to 0),
        'v' to (1 to 0),
        '<' to (0 to -1),
        '>' to (0 to 1),
    )

    data class Element(
        val y: Int,
        val x: Int,
        val c: Char,
        val p: String,
    )

    fun getc(c: Char): Pair<Int, Int> {
        for (i in rows.indices) {
            for (j in rows[i].indices) {
                if (rows[i][j] == c) {
                    return i to j
                }
            }
        }
        return -1 to -1
    }
}

class NumericKeypad: Keypad() {
    init {
        rows = listOf(
            listOf('7', '8', '9'),
            listOf('4', '5', '6'),
            listOf('1', '2', '3'),
            listOf('#', '0', 'A'),
        )

        y = 3
        x = 2
    }
}

class DirectionalKeypad: Keypad() {
    init {
        rows = listOf(
            listOf('#', '^', 'A'),
            listOf('<', 'v', '>'),
        )

        y = 0
        x = 2
    }
}