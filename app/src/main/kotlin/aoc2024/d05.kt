package aoc2024

import java.io.BufferedReader
import java.util.TreeSet

class D05: Solution {
    override val day = 5

    var rules = HashMap<Int, HashSet<Int>>()
    var updates = listOf<List<Int>>()

    override fun part1(reader: BufferedReader): Int {
        parse(reader)

        return updates
            .filter{ isOrderCorrect(it) }
            .map{ it[it.size / 2] }
            .sum()
    }

    override fun part2(reader: BufferedReader): Int {
        parse(reader)

        return updates
            .filter{ !isOrderCorrect(it) }
            .map{ it.sortedWith(cmp) }
            .map{ it[it.size / 2] }
            .sum() 
    }

    private fun parse(reader: BufferedReader) {
        val lines = reader.lineSequence().toList()
        
        lines
            .takeWhile{ line -> line != "" }
            .map{ line -> line.split("|") }
            .map{ pages -> pages[0].toInt() to pages[1].toInt() }
            .forEach{ (u, v) -> rules.getOrPut(u) { hashSetOf() }.add(v) }

        updates = lines
            .dropWhile{ line -> line != "" }
            .filter{ line -> line != "" }
            .map{ line -> line.split(",") }
            .map{ pages -> pages.map{ it.toInt() } }
    }

    private fun isOrderCorrect(pages: List<Int>): Boolean {
        return (0 until pages.size).asSequence()
            .map{ i ->
                ((i + 1) until pages.size).asSequence()
                    .map{ j -> rules.getOrDefault(pages[i], hashSetOf()).contains(pages[j]) }
                    .all{ it }
            }
            .all{ it }
    }

    private val cmp = Comparator<Int> { u, v -> 
        if (v in rules.getOrDefault(u, mutableListOf())) {
            -1
        } else {
            1
        }
    } 
}