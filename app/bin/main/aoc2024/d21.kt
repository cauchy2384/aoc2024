package aoc2024

import java.io.BufferedReader
import java.util.PriorityQueue

import kotlin.math.abs

class D21(val last: Int): Solution<Long> {
    override val day = 21

    override fun part1(reader: BufferedReader): Long {
        var ans = reader.lineSequence()
            .map{ 
                it to score(it, last)
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
            .map{ 
                println(it)
                it to score(it, 26)
            }
            .map{ (_a, _b) -> 
                println("$_a $_b")
                val a = _a.filter { it.isDigit() }.toLong()
                val b = _b
                println("$b * $a")
                a * b
            }
            .sum() 

        return ans
    }

    var numeric: NumericKeypad = NumericKeypad()
    var directional: DirectionalKeypad = DirectionalKeypad()

    // val caches = Array(26) { mutableMapOf<String, Int>() }

    fun score(input: String, level: Int): Int {
        // println("$level $input")
        var ans = 0

        when (level) {
            0 -> {
                ans = directional.typeMany(input)
                    .map{ it.length }
                    .min()
            }
            last -> {
                ans = numeric.typeMany(input)
                    .map{ score(it, level - 1) }
                    .min()
            }
            else -> {
                ans = directional.typeMany(input)
                    .map{ score(it, level - 1) }
                    .min()     
            }
        }

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
                // println("$c $path ${path.length} $best")
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

    fun type(input: String): String {
        var ans = ""
        
        input.forEach{
            ans += typeChar(it)
        }

        return ans
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

    fun typeChar(c: Char): String {
        if (rows[y][x] == c) {
            return "A"
        }

        val res = mutableListOf<String>()
        val q = ArrayDeque<Element>()
        q.add(Element(y, x, '.', ""))

        val visited = HashSet<Pair<Int, Int>>()
        while(q.isNotEmpty()) {
            val el = q.removeFirst()
            val (y, x, pc, path) = el

            if (visited.contains(y to x)) {
                continue
            }
            visited.add(y to x)
            if (y < 0 || y >= rows.size || x < 0 || x >= rows[y].size) {
                continue
            }
            if (rows[y][x] == c) {
                this.y = y
                this.x = x
                path.toCharArray()
                    .sortedWith(
                        compareBy( { -dist(it) })
                    )
                    .joinToString("")
                return path + 'A'
            }
            if (rows[y][x] == '#') {
                continue
            }

            if (pc != '.') {
                val (dy, dx) = directions[pc]!!
                q.add(Element(y + dy, x + dx, pc, path + pc))
            }
            for (dir in directions.keys) {
                if (dir == pc) {
                    continue
                }
                val (dy, dx) = directions[dir]!!
                q.add(Element(y + dy, x + dx, dir, path + dir))
            } 
        }

        return "!!!!!!!!!!!!!!"
    }

    fun reverse(input: String): String {
        var ans = ""

        var (y, x) = this.y to this.x
        for (c in input) {
            if (c != 'A') {
                val (dy, dx) = directions[c]!!
                y += dy
                x += dx
            } else {
                ans += rows[y][x]
            }
        }

        return ans
    }

    fun dist(c: Char): Int {
        val (ya, xa) = getc('A')      
        val (y, x) = getc(c)
        return abs(ya - y) + abs(xa - x)      
    }

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