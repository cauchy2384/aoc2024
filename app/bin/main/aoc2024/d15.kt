package aoc2024

import java.io.BufferedReader
import java.io.StringReader


class D15: Solution<Long> {
    override val day = 15

    override fun part1(reader: BufferedReader): Long {
        val lines: List<String> = reader.lineSequence().toList()
        val map = lines.takeWhile{ it != "" }
            
        val mx = Matrix<Char>(
            BufferedReader(
                StringReader(
                    map.joinToString("\n")
                )
            )
        ) { it }

        val start = mx.filter{ it.third == '@' }.first()
        var (i, j, _) = start
        val moves = lines.dropWhile{ it != "" }.drop(1)
        moves.forEach{ 
            it.toCharArray()
                .forEach{ 
                    val (i2, j2, _) = move(mx, i, j, it)
                    i = i2
                    j = j2
                }
        }

        return mx.filter{ it.third == 'O' }
            .map{ (i, j) -> i.toLong() * 100L + j.toLong() }
            .sum()
    }

    val dirs = HashMap<Char, Pair<Int, Int>>()
    init {
        dirs['^'] = -1 to 0
        dirs['>'] = 0 to 1
        dirs['v'] = 1 to 0
        dirs['<'] = 0 to -1
    }


    fun move(mx: Matrix<Char>, i: Int, j: Int, c: Char): Triple<Int, Int, Boolean> {
        val (dy, dx) = dirs[c]!!
        val (y, x) = i + dy to j + dx

        when (mx.get(y, x)) {
            '.' -> {
                mx.set(y, x, mx.get(i, j)!!)
                mx.set(i, j, '.')
                return Triple(y, x, true)
            }
            'O' -> {
                val (_, _, ok) = move(mx, y, x, c)
                if (ok) {
                    mx.set(y, x, mx.get(i, j)!!)
                    mx.set(i, j, '.')
                    return Triple(y, x, true)
                } else {
                    return Triple(i, j, false)
                }
            }
            '#' -> return Triple(i, j, false)
            else -> return Triple(i, j, false) 
        }
    }

    override fun part2(reader: BufferedReader): Long {
        val lines: List<String> = reader.lineSequence().toList()
        val map = lines.takeWhile{ it != "" }
            .map{ 
                it.map { char ->
                    when (char) {
                        'O' -> "[]"
                        '@' -> "@."
                        else -> char.toString() + char.toString()
                    }
                }.joinToString("")
            }
            
        val mx = Matrix<Char>(
            BufferedReader(
                StringReader(
                    map.joinToString("\n")
                )
            )
        ) { it }

        val start = mx.filter{ it.third == '@' }.first()
        var (i, j, _) = start

        val moves = lines.dropWhile{ it != "" }.drop(1)
        moves.forEach{ 
            it.toCharArray()
                .forEach{
                    if (canmove2(mx, i, j, it)) {
                        val (i2, j2) = move2(mx, i, j, it)
                        mx.set(i, j, '.')
                        i = i2
                        j = j2
                    }
                }
        }

        return mx.filter{ it.third == '[' }
            .map{ (i, j) -> i.toLong() * 100L + j.toLong() }
            .sum()
    }

    fun canmove2(mx: Matrix<Char>, i: Int, j: Int, dir: Char): Boolean {
        val deque = ArrayDeque<Pair<Int, Int>>()
        deque.addLast(Pair(i, j))

        val (dy, dx) = dirs[dir]!!
        while (deque.isNotEmpty()) {
            val (i, j) = deque.removeFirst()
            val (y, x) = i + dy to j + dx

            val c = mx.get(i, j)!!
            when (c) {
                '@' -> deque.addLast(Pair(y, x))
                '[' -> {
                    if (dir == '^' || dir == 'v') {
                        deque.addLast(Pair(y, x))
                        deque.addLast(Pair(y, x + 1))
                    } else {
                        deque.addLast(Pair(y, x))
                    }       
                }          
                ']' -> {
                    if (dir == '^' || dir == 'v') {
                        deque.addLast(Pair(y, x - 1))
                        deque.addLast(Pair(y, x))
                    } else {
                        deque.addLast(Pair(y, x))
                    } 
                }
                '.' -> {}
                '#' -> return false
                else -> return false 
            }
        }

        return true
    }

    fun move2(mx: Matrix<Char>, i: Int, j: Int, dir: Char): Pair<Int, Int> {
        val deque = ArrayDeque<Triple<Int, Int, Char>>()
        deque.addLast(Triple(i, j, '.'))

        val (dy, dx) = dirs[dir]!!
        while (deque.isNotEmpty()) {
            val (i, j, newC) = deque.removeFirst()
            val (y, x) = i + dy to j + dx

            val c = mx.get(i, j)!!
            mx.set(i, j, newC)
            when (c) {
                '@' -> deque.addLast(Triple(y, x, c))
                '[' -> {
                    if (dir == '^' || dir == 'v') {
                        deque.addLast(Triple(y, x, c))
                        mx.set(i, j + 1, '.')
                        deque.addLast(Triple(y, x + 1, ']'))
                    } else {
                        deque.addLast(Triple(y, x, c))
                    }                 
                }
                ']' -> {
                    if (dir == '^' || dir == 'v') {
                        mx.set(i, j - 1, '.')
                        deque.addLast(Triple(y, x - 1, '['))
                        deque.addLast(Triple(y, x, c))
                    } else {
                        deque.addLast(Triple(y, x, c))
                    } 
                }
                else -> {}
            }
        }

        return i + dy to j + dx
    }
}
