package aoc2024

import java.io.BufferedReader

class D07: Solution<Long> {
    override val day = 7

    override fun part1(reader: BufferedReader): Long {
        return reader.lineSequence()
            .map{ line -> line.split(":")}
            .map{ it[0] to it[1].split(" ").filter{ it.isNotEmpty() } }
            .map{ (a, b) -> a.toLong() to b.map{ it.toLong() } }
            .filter{ (target, nums) -> check(target, nums, false) }
            .sumOf{ it.first }
    }

    override fun part2(reader: BufferedReader): Long {
        return reader.lineSequence()
            .map{ it.split(":")}
            .map{ it[0] to it[1].split(" ").filter{ it.isNotEmpty() } }
            .map{ (a, b) -> a.toLong() to b.map{ it.toLong() } }
            .filter{ (target, nums) -> check(target, nums, true) }
            .sumOf{ it.first }
    }

    private fun check(target: Long, nums: List<Long>, withConcat: Boolean): Boolean {
        fun rec(i: Int, sum: Long): Boolean {
            if (i == nums.size) {
                return sum == target
            }

            return listOfNotNull(
                rec(i + 1, sum + nums[i]),
                rec(i + 1, sum * nums[i]),
                if (withConcat) rec(i + 1, concat(sum, nums[i])) else null,
            ).any{ it }
        }

        return rec(0, 0)
    }

    fun concat(a: Long, b: Long): Long {
        return (a.toString() + b.toString()).toLong()
    }
}
