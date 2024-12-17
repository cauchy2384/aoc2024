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

        val cpu = CPU(instructions)
        cpu.A = regs[0]
        cpu.B = regs[1]
        cpu.C = regs[2]

        cpu.exec()

        return cpu.ans()
    }

    override fun part2(reader: BufferedReader): String {
        val lines = reader.lineSequence().toList()
        val program = lines.dropWhile{ it != "" }.drop(1).map{ it.split(" ")[1] } 
        val instructions = program.flatMap{ it.split(",").map{ it.toLong()} }

        val cpu = CPU(instructions)
        
        var opts = mutableListOf(0L)
        for (ins in instructions.reversed()) {
            opts = opts
                .flatMap{ (it * 8L until (it + 1L) * 8L).toList()}
                .map{ a ->
                    cpu.reset()
                    cpu.A = a
                    do {
                        cpu.tick()
                    } while (cpu.ip in 1 until instructions.size)
                    a to cpu.stdout.last()
                }
                .filter{ (_, b) -> b == ins }
                .map{ it.first }
                .toMutableList()
        }
        
        if (opts.isEmpty()) {
            return "failed"
        } 
        return opts[0].toString()
    }
}

class CPU(val instructions: List<Long>) {
    var A: Long = 0L
    var B: Long = 0L
    var C: Long = 0L

    var ip = 0
    val stdout = mutableListOf<Long>()

    fun reset() {
        A = 0L
        B = 0L
        C = 0L
        ip = 0
        stdout.clear()
    }

    fun exec() {
        ip = 0
        while (ip < instructions.size) {
            tick()
        }
    }

    fun ans(): String {
        return stdout.joinToString(",")
    }

    fun tick() {
        val opcode = instructions[ip].toInt()
        val literal = instructions[ip + 1]

        var combo = when(literal) {
            4L -> A
            5L -> B
            6L -> C
            else ->literal
        }

        fun denum(): Long {
            val num = A
            val denum = Math.pow(2.0, combo.toDouble()).toLong()
            return num / denum
        }

        when (opcode) {
            0 -> A = denum()
            1 -> B = B xor literal.toLong()
            2 -> B = combo % 8L
            3 -> {
                if (A != 0L) {
                    ip = literal.toInt()
                    return
                }            
            }
            4 -> B = B xor C
            5 -> stdout += combo % 8L
            6 -> B = denum()
            7 -> C = denum()
            else -> {} 
        }

        ip += 2
        return
    }    
}