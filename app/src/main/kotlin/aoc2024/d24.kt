package aoc2024

import java.io.BufferedReader
import java.io.File
import kotlin.math.max

class D24: Solution<Long> {
    override val day = 24

    override fun part1(reader: BufferedReader): Long {
        val lines = reader.lineSequence().toList()
        
        val gates = HashMap<String, Gate>()
        val ins = HashMap<String, HashSet<String>>()
        val outs = HashMap<String, HashSet<Pair<String, String>>>()

        lines.takeWhile{ it != "" }
            .map{ it.split(": ") }
            .map{ it[0] to it[1].toInt() }
            .map{ Gate(it.first, it.second, it.second, "OR") }
            .forEach{ 
                gates[it.name] = it 
                ins[it.name] = HashSet<String>()
            }


        lines.dropWhile({ it != ""})
            .drop(1)
            .map{ it.split(" ") }
            .forEach{ 
                val out = it[4]
                gates[out] = Gate(out, null, null, it[1])  
                val left = it[0]
                val right = it[2]
                ins.getOrPut(out) { HashSet() }.apply {
                    add(left)
                    add(right)
                }
                outs.getOrPut(left) { HashSet() }.apply { add("l" to out) }
                outs.getOrPut(right) { HashSet() }.apply { add("r" to out) }
            }

        // println(gates)
        // println(ins)
        // println(outs)

        val q = ArrayDeque<String>()
        for (key in ins.keys) {
            val edges = ins[key]!!
            if (edges.size == 0) {
                q.add(key)
            } 
        }

        // println(q)

        while (q.isNotEmpty()) {
            val name = q.removeFirst()
            if (outs[name] == null) {
                continue
            }
            val edges = outs[name]!!
            // println("$name ${gates[name]!!.value()} $edges")
            for (e in edges) {
                val (l, v) = e
                // println("\t${l} $v")
                val g = gates[v]!!
                if (l == "l") {
                    g.a = gates[name]!!.value()
                } else {
                    g.b = gates[name]!!.value()
                }
                gates[v] = g
                // println("\t$v ${gates[v]!!.a} ${gates[v]!!.a}")
                // println("\t${ins[v]}")
                ins[v]!!.remove(name)
                // println("\t${ins[v]}")
                if (ins[v]!!.size == 0) {
                    // println("$v ${gates[v]!!.value()}")
                    q.add(v)
                }
            }
        }

        var i = 0
        var ans = 0L
        while (true) {
            val name = "z%02d".format(i)
            if (gates[name] == null) {
                break
            }
            val v = gates[name]!!.value() 
            // println("$name ${v}")
            if (v == 1) {
                ans = ans or (1L shl i)
            }
            i += 1
        }

        // println(ans.toString(2).padStart(Long.SIZE_BITS, '0'))
        return ans
    }

    class Gate(val name: String, var a: Int?, var b: Int?, val t: String) {
        fun value(): Int? {
            if (a == null || b == null) {
                return null
            }
            return when (t) {
                "AND" -> a!! and b!!
                "OR" -> a!! or b!!
                else -> (a!! xor b!!) and 1
            }
        }
    }

    var lines = listOf<String>()
    var gates = HashMap<String, Gate>()
    var ins = HashMap<String, HashSet<String>>()
    var outs = HashMap<String, HashSet<Pair<String, String>>>()

    fun parse() {
        gates = HashMap<String, Gate>()
        ins = HashMap<String, HashSet<String>>()
        outs = HashMap<String, HashSet<Pair<String, String>>>()

        lines.takeWhile{ it != "" }
            .map{ it.split(": ") }
            .map{ it[0] to it[1].toInt() }
            .map{ Gate(it.first, it.second, it.second, "OR") }
            .forEach{ 
                gates[it.name] = it 
                ins[it.name] = HashSet<String>()
            }


        lines.dropWhile({ it != ""})
            .drop(1)
            .map{ it.split(" ") }
            .forEach{ 
                val out = it[4]
                gates[out] = Gate(out, null, null, it[1])  
                val left = it[0]
                val right = it[2]
                ins.getOrPut(out) { HashSet() }.apply {
                    add(left)
                    add(right)
                }
                outs.getOrPut(left) { HashSet() }.apply { add("l" to out) }
                outs.getOrPut(right) { HashSet() }.apply { add("r" to out) }
            } 
    }

    override fun part2(reader: BufferedReader): Long {
        lines = reader.lineSequence().toList()

        val subs = HashMap<String, String>()
        // subs["z16"] = "mvp"
        // subs["mvp"] = "z16"
        subs["z16"] = "pbv"
        subs["pbv"] = "z16"

        subs["z36"] = "fbq"
        subs["fbq"] = "z36"   

        subs["z23"] = "qqp"
        subs["qqp"] = "z23"  

        subs["qff"] = "qnw"
        subs["qnw"] = "qff"  

        val ans = subs.keys.sorted().joinToString(",")
        println(ans)

        parseSubs(subs)
        // println(outs)

        val q = ArrayDeque<String>()
        for (i in 0 until 64) {
            val x = "x%02d".format(i)
            val y = "y%02d".format(i)
            if (gates[x] != null) {
                q.add(x)
            }
            if (gates[y] != null) {
                q.add(y)
            }
        }
        File("graph.dot").bufferedWriter().use { out ->
            out.write("digraph {")
            val ls = HashSet<String>()
            while (q.isNotEmpty()) {
                val name = q.removeFirst()
                if (outs[name] == null) {
                    continue
                }
                for (e in outs[name]!!) {
                    val (l, v) = e
                    val s = "\t$name -> $v" 
                    // val s = "\t$name -> $v [label=\"${gates[name]!!.t}\"]" 
                    ls.add(s)
                    // println(s)
                    q.add(v)
                }
            }
            for (k in gates.keys) {
                val s = "\t${k} [label=\"${gates[k]!!.name}\n${gates[k]!!.t}\"]"
                ls.add(s)
                // println(s)
            }
            for (k in ls) {
                out.write(k)
            }
            out.write("}")
        }

        // println("nodes: ${gates.size}")
        // for (i in 0 until 5) {
        //     println("$i ${check(i)}")
        // }

        return -1L
    }

    fun parseSubs(_subs: HashMap<String, String>) {
        fun subs(s: String): String {
            if (s in _subs) {
                return _subs[s]!!
            }
            return s
        }

        gates = HashMap<String, Gate>()
        ins = HashMap<String, HashSet<String>>()
        outs = HashMap<String, HashSet<Pair<String, String>>>()

        lines.takeWhile{ it != "" }
            .map{ it.split(": ") }
            .map{ it[0] to it[1].toInt() }
            .map{ Gate(it.first, it.second, it.second, "OR") }
            .forEach{ 
                gates[it.name] = it 
                ins[it.name] = HashSet<String>()
            }


        lines.dropWhile({ it != ""})
            .drop(1)
            .map{ it.split(" ") }
            .forEach{ 
                val out = subs(it[4])
                gates[out] = Gate(out, null, null, it[1])  
                val left = it[0]
                val right = it[2]
                ins.getOrPut(out) { HashSet() }.apply {
                    add(left)
                    add(right)
                }
                outs.getOrPut(left) { HashSet() }.apply { add("l" to out) }
                outs.getOrPut(right) { HashSet() }.apply { add("r" to out) }
            } 
    }

    data class testcase(val a: Int, val b: Int, val c: Int, val d: Int)

    fun check(i: Int): Boolean {
        val x = "x%02d".format(i)
        val y = "y%02d".format(i)
        val z0 = "z%02d".format(i)
        val z1 = "z%02d".format(i + 1)
        
        val cases = listOf(
            testcase(0, 0, 0, 0),
            testcase(0, 1, 1, 0),
            testcase(1, 0, 1, 0),
            testcase(1, 1, 0, 1),
        )

        for (c in cases) {
            println("\tchecking $c")
            set(x, c.a)
            set(y, c.b)
            solve(ArrayDeque(listOf(x, y)))
            println("\tresult: ${gates[z0]!!.value()} ${gates[z1]!!.value()}")
            if (gates[z0]!!.value() != c.c) {
                return false
            }
            if (gates[z1]!!.value() != c.d) {
                return false
            }
        }
        return true
    }

    fun set(name: String, v: Int) {
        gates[name]!!.a = v
        gates[name]!!.b = v
    }

    fun solve(q: ArrayDeque<String>) {
        var it = 0
        while (q.isNotEmpty()) {
            val name = q.removeFirst()
            if (outs[name] == null) {
                continue
            }
            val edges = outs[name]!!
            // println("$name ${gates[name]!!.value()} $edges")
            for (e in edges) {
                val (l, v) = e
                // println("\t${l} $v")
                val g = gates[v]!!
                if (l == "l") {
                    g.a = gates[name]!!.value()
                } else {
                    g.b = gates[name]!!.value()
                }
                gates[v] = g
                // println("\t$v ${gates[v]!!.a} ${gates[v]!!.a}")
                // println("\t${ins[v]}")
                ins[v]!!.remove(name)
                // println("\t${ins[v]}")
                if (ins[v]!!.size == 0) {
                    // println("$v ${gates[v]!!.value()}")
                    q.add(v)
                }
            }
            it += 1
            if (it > 10000) {
                return
            }
        }
    }

    class Node(val name: String, val a: String?, val b: String?) {
        var out: String? = null
    
        override fun toString(): String {
            return "name: $name, a: $a, b: $b"
        }
    }
}
