package aoc2024

import java.io.BufferedReader

class D06: Solution {
    override val day = 6

    override fun part1(reader: BufferedReader): Int {
        val mx = Labirynth(reader)

        val (i, j, _) = mx.filter { (_, _, c) -> c == '^' }.first()

        mx.patrol(i, j, 0)

        return mx.filter { (_, _, c) -> c == 'X' }
            .count()
    }

    override fun part2(reader: BufferedReader): Int {
        val mx = Labirynth(reader)

        val (si, sj, _) = mx.filter { (_, _, c) -> c == '^' }.first()

        mx.patrol(si, sj, 0)

        var opts =  mx.filter { (_, _, c) -> c == 'X' }
            .map{ (i, j, _) -> i to j}

        mx.forEach { (i, j, c) -> if (c == 'X') mx.set(i, j, '.') } 

        return opts
            .map{ (i, j) -> 
                mx.set(i, j, '#')
                val res = mx.patrol(si, sj, 0)
                mx.set(i, j, '.')
                mx.forEach { (i, j, c) -> if (c == 'X') mx.set(i, j, '.') }
                res
            }
            .filter { it }
            .count()
    }
}

class Labirynth: Matrix<Char> {
    constructor(reader: BufferedReader): super(
        reader = reader,
        converter = { it }
    )   

    tailrec fun patrol(i: Int, j: Int, dir: Int, visited: MutableSet<Triple<Int, Int, Int>> = mutableSetOf()): Boolean {
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return false
        }

        if (visited.contains(Triple<Int, Int, Int>(i, j, dir))) {
            return true
        }
        visited.add(Triple<Int, Int, Int>(i, j, dir))

        set(i, j, 'X')
        var ni = i
        var nj = j
        var dir = dir
        while (true) {
            when (dir) {
                0 -> { ni = i - 1; nj = j }
                1 -> { ni = i; nj = j + 1 }
                2 -> { ni = i + 1; nj= j }
                3 -> { ni = i; nj = j - 1 }   
                else -> {}
            }
            if (get(ni, nj) != '#') {
                break
            }
            dir = (dir + 1) % 4
        }
        return patrol(ni, nj, dir, visited)
    }

}