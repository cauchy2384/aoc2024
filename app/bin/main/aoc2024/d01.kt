package aoc2024

import java.io.BufferedReader

import kotlin.math.abs

class D01: Solution<Int> {
    override val day = 1

    override fun part1(reader: BufferedReader): Int {
        var (left, right) = reader.lineSequence()
            .map { line -> line.split(" ").filter(String::isNotEmpty) }
            .map { nums -> nums.first().toInt() to nums.last().toInt() }
            .unzip()
            .let{ (l, r) -> l.sorted() to r.sorted() }

        return left.zip(right) { l, r -> abs(l - r)}
            .sum()
    }

    override fun part2(reader: BufferedReader): Int {
        var (nums, count) =  reader.lineSequence()
            .map { line -> line.split(" ").filter(String::isNotEmpty) }
            .map { nums -> nums.first().toInt() to nums.last().toInt() }
            .unzip()
            .let{ (l, r) -> l to r.groupingBy { it }.eachCount() }

        return nums.fold(0) { ans, num -> ans + num * count.getOrDefault(num, 0) }
    }
}
    