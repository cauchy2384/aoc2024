package aoc2024

import java.io.BufferedReader

import kotlin.math.max

class D22: Solution<Long> {
    override val day = 22

    override fun part1(reader: BufferedReader): Long {
        return reader.lineSequence()
            .map{ it.toLong() }
            .map{ generate(it, 2000) }
            .sum()
    }

    fun generate(secret: Long, times: Long): Long {
        var ans = secret
        for (i in 0 until times) {
            ans = next(ans)
        }
        return ans
    }

    fun next(n: Long): Long {
        var ans = n
        var secret = n

        ans = ans * 64L
        ans = ans xor secret
        ans = ans % 16777216
        secret = ans

        ans = secret / 32L 
        ans = ans xor secret
        ans = ans % 16777216
        secret = ans

        ans = secret * 2048L
        ans = ans xor secret
        ans = ans % 16777216

        return ans
    }

    override fun part2(reader: BufferedReader): Long {
        val list = reader.lineSequence()
            .map{ it.toLong() }
            .map{ changes(it, 2000) }
            .toList()
            
        var agg = HashMap<List<Int>, Int>()
        for (l in list) {
            all(l, agg)
        }

        return agg.values.max().toLong()
    }

    fun changes(_secret: Long, times: Long): List<Pair<Int, Int>> {
        var secret = _secret
        val ans = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until times) {
            val nxt = next(secret)
            val a = secret.toString().last().digitToInt().toInt()
            val b = nxt.toString().last().digitToInt().toInt()
            ans.add(b - a to b)
            secret = nxt
        }

        return ans
    }

    fun all(list: List<Pair<Int, Int>>, agg: HashMap<List<Int>, Int>) {
        val used = HashSet<List<Int>>()

        for (i in 4 .. list.size) {
            val sub = list.subList(i - 4, i).map{ it.first }
            if (used.contains(sub)) {
                continue
            }
            used.add(sub)
            agg[sub] = agg.getOrDefault(sub, 0) + list[i - 1].second
        } 
    }
}
