package aoc2024

import java.io.BufferedReader

class D17: Solution<String> {
    override val day = 17

    override fun part1(reader: BufferedReader): String {
        val lines = reader.lineSequence().toList()

        val regs = lines.takeWhile{ it != "" }
            .map { it.split(" ").last().toLong() }
            .toMutableList()
        
        val instructions = lines.dropWhile{ it != "" }
            .drop(1)
            .map{ it.split(" ")[1] }
            .flatMap{ it.split(",").map{ it.toLong()} }

        return exec(regs, instructions)
    }
    
    fun exec(regs: MutableList<Long>, instructions: List<Long>, limit: Long = 1000L): String {
        var ip = 0
        var ans = mutableListOf<String>()
        var ticks = 0L
        while (ip < instructions.size) {
            val opcode = instructions[ip].toInt()
            val literal = instructions[ip + 1]
            var combo = literal
            if (literal in 4L..6L) {
                combo = regs[literal.toInt() - 4]
            }

            when (opcode) {
                0 -> {
                    val num = regs[0]
                    val denum = Math.pow(2.0, combo.toDouble()).toLong()
                    regs[0] = num / denum
                    ip += 2
                }
                1 -> {
                    regs[1] = regs[1] xor literal.toLong()
                    ip += 2
                }
                2 -> {
                    regs[1] = combo % 8L
                    ip += 2
                }
                3 -> {
                    if (regs[0] != 0L) {
                        ip = literal.toInt()
                    } else {
                        ip += 2
                    }
                }
                4 -> {
                    regs[1] = regs[1] xor regs[2]
                    ip += 2
                }
                5 -> {
                    ans += (combo % 8L).toString()
                    ip += 2
                }
                6 -> {
                    val num = regs[0]
                    val denum = Math.pow(2.0, combo.toDouble()).toLong()
                    regs[1] = num / denum
                    ip += 2 
                }
             
                7 -> {
                    val num = regs[0]
                    val denum = Math.pow(2.0, combo.toDouble()).toLong()
                    regs[2] = num / denum
                    ip += 2 
                }
                else -> ip += 2
            }

            ticks += 1
            if (ticks > limit) {
                return "TIMEOUT"
            }
        }

        return ans.joinToString(",")
    }

    override fun part2(reader: BufferedReader): String {
        val lines = reader.lineSequence().toList()
        val program = lines.dropWhile{ it != "" }.drop(1).map{ it.split(" ")[1] } 
        val instructions = program.flatMap{ it.split(",").map{ it.toLong()} }

        var opts = mutableListOf<Long>()
        opts += 0L

        var i = 0L
        for (ins in instructions.reversed()) {
            i += 1L
            val opts2 = mutableListOf<Long>()
            opts.forEach{
                var min = it * 8L
                while (min / 8L < (it + 1L)) {
                    opts2 += min
                    min += 1L
                }
            }
            opts = opts2.asSequence()
                .map{ a ->
                    val (na, b) = calc(a)
                    Triple(a, na, b)
                }
                .filter{ (a, na, b) -> b == ins }
                .map{ it.first }
                .toMutableList()
        }

        return opts[0].toString()
    }

    fun calc(_a: Long): Pair<Long, Long> {
        var a = _a
        var b = a % 8L   
        b = b xor 5L
        var c = a / Math.pow(2.0, b.toDouble()).toLong()
        b = b xor 6L
        a = a / 8L
        b = b xor c
        b = b % 8L
        return a to b
    }
}

/*
2,4,
1,5,
7,5,
1,6,
0,3,
4,0,
5,5,
3,0
*/