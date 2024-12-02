package aoc2024

import java.io.BufferedReader

interface Solution {
    val day: Int

    fun part1(reader: BufferedReader): Int 
    fun part2(reader: BufferedReader): Int 
}