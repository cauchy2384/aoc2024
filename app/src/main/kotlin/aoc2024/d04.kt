package aoc2024

import java.io.BufferedReader

class D04: Solution {
    override val day = 4

    override fun part1(reader: BufferedReader): Int {
        val mx = XMAStrix(reader) 
        return mx.map { (i, j, _) ->
            (0 until Direction.values().size).asSequence()
                .map{ mx.searchInDirection(i to j, 0, "XMAS", it) }
                .sum()
            }
            .sum()
    }

    override fun part2(reader: BufferedReader): Int {
        val mx = XMAStrix(reader) 
        
        return mx.filter{ mx.hasXMAS(it) }
            .count()
    }
}

enum class Direction(val delta: Pair<Int, Int>) {
    DOWN(1 to 0),
    UP(-1 to 0),
    RIGHT(0 to 1),
    LEFT(0 to -1),
    DOWN_RIGHT(1 to 1),
    DOWN_LEFT(1 to -1),
    UP_RIGHT(-1 to 1),
    UP_LEFT(-1 to -1)
}

class XMAStrix: Matrix<Char> {
    constructor(reader: BufferedReader): super(
        reader = reader,
        converter = { it }
    )   

    val directions = Direction.values()

    fun searchInDirection(coordinates: Pair<Int, Int>, k: Int, word: String, dir: Int): Int {
        val (i, j) = coordinates

        if (!isAt(i, j, word[k])) {
            return 0
        }

        if (k == word.length - 1) {
            return 1
        }

        val (di, dj) = directions[dir].delta
        return searchInDirection(i + di to j + dj, k + 1, word, dir)
    }

    fun hasXMAS(element: Element<Char>): Boolean { 
        val (i, j, c) = element
        return c == 'A' && hasLettersSameSide(i, j, 'M') && hasLettersSameSide(i, j, 'S') 
    }

    fun hasLettersSameSide(i: Int, j: Int, c: Char): Boolean {
        val top = isAt(i - 1, j - 1, c) && isAt(i - 1, j + 1, c)
        val bot = isAt(i + 1, j - 1, c) && isAt(i + 1, j + 1, c)
        val left = isAt(i - 1, j - 1, c) && isAt(i + 1, j - 1, c)
        val right = isAt(i - 1, j + 1, c) && isAt(i + 1, j + 1, c)
        return top || bot || left || right
    }
}
