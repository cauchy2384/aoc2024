package aoc2024

import java.io.BufferedReader

interface Solution<T> {
    val day: Int

    fun part1(reader: BufferedReader): T 
    fun part2(reader: BufferedReader): T 
}