package aoc2024

import java.io.BufferedReader

import kotlin.math.abs

class D02: Solution {
    override val day = 2

    override fun part1(reader: BufferedReader): Int {
        var ans = 0
        reader.forEachLine {
            line -> 
                val nums = line.split(" ")
                .map { v -> v.toInt() }
                val sign = nums[1] - nums[0] > 0
                var safe = true 
                for (i in 1 until nums.size) {
                    val sign2 = nums[i] - nums[i - 1] > 0 
                    val safe2 = safe and (abs(nums[i] - nums[i - 1]) >= 1 && abs(nums[i] - nums[i - 1]) <= 3)
                    safe = safe and safe2 and (sign == sign2)
                }
                if (safe) {
                    ans += 1
                }
        }
        return ans
    }

    override fun part2(reader: BufferedReader): Int {
        var ans = 0
        reader.forEachLine {
            line -> 
                val numsBase = line.split(" ")
                .map { v -> v.toInt() }
                var superSafe = false 
                for (k in 0 until numsBase.size) {
                    val nums = numsBase.filterIndexed { index, _ -> index != k }
                    val sign = nums[1] - nums[0] > 0
                    var safe = true 
                    for (i in 1 until nums.size) {
                        val sign2 = nums[i] - nums[i - 1] > 0 
                        val safe2 = safe and (abs(nums[i] - nums[i - 1]) >= 1 && abs(nums[i] - nums[i - 1]) <= 3)
                        safe = safe and safe2 and (sign == sign2)
                    }
                    superSafe = superSafe or safe
                }
                if (superSafe) {
                    ans += 1
                }
        }
        return ans
    }
}
    