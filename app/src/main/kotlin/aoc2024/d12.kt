package aoc2024

import java.io.BufferedReader
import kotlin.Triple
import kotlin.Pair

class D12: Solution<Long> {
    override val day = 12

    override fun part1(reader: BufferedReader): Long {
        val mx = Matrix(reader, { it })

        val ans = mx.map{ (i, j, c) -> 
                dfs(mx, c, c.lowercaseChar(),  i, j)
            }
            .filter { (a, _) -> a != 0L  }
            .map{ (a, p) -> 
                a * p 
            }
            .sum()

        return ans
    }

    fun dfs(mx: Matrix<Char>, c: Char, repC: Char, i: Int, j: Int): Pair<Long, Long> {
        if (i < 0 || i >= mx.m || j < 0 || j >= mx.n) {
            return 0L to 0L
        }
        if (mx.get(i, j) != c || mx.get(i, j) == repC) {
            return 0L to 0L
        }
        mx.set(i, j, repC)

        var (area, perimeter) = 1L to 0L
        for ((dx, dy) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
            val (x, y) = i + dx to j + dy
            if (mx.get(x, y) != c && mx.get(x, y) != repC) {
                perimeter += 1L
            } else {
                val (a, p) = dfs(mx, c, repC, x, y)
                area += a
                perimeter += p
            }
        }

        return area to perimeter
    }

    override fun part2(reader: BufferedReader): Long {
        val mx = Matrix(reader, { it })

        val ans = mx.map{ (i, j, c) -> 
                dfs2(mx, c, c.lowercaseChar(),  i, j)
            }
            .filter { (a, _) -> a != 0L  }
            .map{ (a, p) -> a to p/2 }
            .map{ (a, p) -> 
                a * p 
            }
            .sum()

        return ans
    }


    fun dfs2(mx: Matrix<Char>, c: Char, repC: Char, i: Int, j: Int): Pair<Long, Long> {
        fun isChar(i: Int, j: Int): Boolean {
           return mx.get(i, j) == c || mx.get(i, j) == repC 
        }

        fun isNotChar(i: Int, j: Int): Boolean {
            return !isChar(i, j)
         }

        if (mx.get(i, j) == repC) {
            return 0L to 0L
        }
        mx.set(i, j, repC)

        var (area, perimeter) = 1L to 0L
        for ((dx, dy) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
            val (y, x) = i + dy to j + dx
            if (isNotChar(y, x)) {
                if (isNotChar(i + dx, j + dy)) {
                    perimeter += 1L
                }
                if (isNotChar(i - dx, j - dy)) {
                    perimeter += 1L
                }

                if (isChar(y + dx, x + dy) && isChar(i + dx, j + dy)) {
                    perimeter += 1L
                }
                if (isChar(y - dx, x - dy) && isChar(i - dx, j - dy)) {
                    perimeter += 1L
                }
            } else {
                val (a, p) = dfs2(mx, c, repC, y, x)
                area += a
                perimeter += p
            }
        }
        
        return area to perimeter
    }
}
