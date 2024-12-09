package aoc2024

import java.io.BufferedReader

data class File(val id: Int, val space: Int)

class D09: Solution<Long> {
    override val day = 9

    override fun part1(reader: BufferedReader): Long {
        val blocks = reader.lineSequence()
            .first()
            .withIndex()
            .map{ (i, c) -> 
                if (i % 2 == 0) File(i / 2, c.digitToInt()) else File(-1, c.digitToInt())
            }
            .flatMap { file -> (0 until file.space).map{ File(file.id, 1) } }
            .map{ it.id }
            .toMutableList()

        var (l, r) = 0 to blocks.size - 1
        while (l < r) {
            if (blocks[l] != -1) {
                l += 1
                continue
            }
            if (blocks[r] == -1) {
                r -= 1
                continue
            }
            val temp = blocks[l]
            blocks[l] = blocks[r]
            blocks[r] = temp
            l += 1
            r -= 1
        }

        return blocks.asSequence()
            .filter { it != -1 }
            .withIndex()
            .sumOf{ (i, v) -> (i * v).toLong() }
    }

    override fun part2(reader: BufferedReader): Long {
        val blocks = reader.lineSequence()
            .first()
            .withIndex()
            .map{ (i, c) -> 
                if (i % 2 == 0) File(i / 2, c.digitToInt()) else File(-1, c.digitToInt())
            }
            .toMutableList()

        for (r in blocks.size - 1 downTo 0) {
            val rBlock = blocks[r]
            if (rBlock.id == -1) {
                continue
            }
            var l = 0
            while (l < r) {
                val lBlock = blocks[l]
                if (lBlock.id != -1 || lBlock.space < rBlock.space) {
                    l += 1
                    continue
                }
                if (lBlock.space == rBlock.space) {
                    blocks[r] = File(-1, rBlock.space)
                    blocks[l] = File(rBlock.id, lBlock.space)
                    break
                }
                val elements = listOf( File(rBlock.id, rBlock.space), File(lBlock.id, lBlock.space - rBlock.space) )
                blocks[r] = File(-1, rBlock.space)
                blocks.removeAt(l)
                blocks.addAll(l, elements)
                break
            }
        }

        return blocks.asSequence()            
            .flatMap { file -> (0 until file.space).map{ File(file.id, 1) } }
            .map{ it.id }
            .withIndex()
            .filter { (i, v) -> v != -1 }
            .sumOf{ (i, v) -> (i * v).toLong() }
    }
}
