package aoc2024

import java.io.BufferedReader

class D23: Solution<Long> {
    override val day = 23

    override fun part1(reader: BufferedReader): Long {
        val graph = HashMap<String, List<String>>()

        reader.lineSequence()
            .map{ it.split("-") }
            .map{ it[0] to it[1] }
            .forEach{ 
                (u, v) -> 
                    graph[u] = graph.getOrDefault(u, listOf()) + v
                    graph[v] = graph.getOrDefault(v, listOf()) + u
            }

        val ans = HashSet<List<String>>()
        for (first in graph.keys) {
            for (second in graph[first]!!) {
                for (third in graph[second]!!) {
                    if (third == first) {
                        continue
                    }
                    if (!graph[third]!!.any { it == first}) {
                        continue
                    }
                    val conn = listOf(first, second, third)
                    if (conn.any{ it.startsWith("t")}) {
                        ans.add(conn.sorted())
                    }
                }    
            }
        }

        return ans.size.toLong()
    }

    override fun part2(reader: BufferedReader): Long {
        val graph = HashMap<String, HashSet<String>>()

        reader.lineSequence()
            .map{ it.split("-") }
            .map{ it[0] to it[1] }
            .forEach{ 
                (u, v) -> 
                    graph.getOrPut(u) { hashSetOf() }.add(v)
                    graph.getOrPut(v) { hashSetOf() }.add(u)
            }

        var ans = listOf<String>()

        fun rec(node: String, path: HashSet<String>) {
            if (node in path) {
                return
            }
            for (prev in path) {
                if (!graph[node]!!.contains(prev)) {
                    return
                }
            }
            path.add(node)
            if (path.size > ans.size) {
                ans = path.toList()
            }
            for (next in graph[node]!!) {
                if (next in path) {
                    continue
                }
                rec(next, path.clone() as HashSet<String>)
            }
        }

        fun check(start: String, edges: List<String>) {
            if (edges.size + 1 < ans.size) {
                return
            } 

            var ok = true
            for (e in edges) {
                val b = edges.filter{ it != e }.filter{ graph[e]!!.contains(it) } 
                if (edges.size - 1 != b.size) {
                    ok = false
                    break
                }
            } 
            if (ok) {
                ans = edges + start
                return
            }

            for (e in edges) {
                check(start, edges.filter{ it != e })
            }
        }

        for (node in graph.keys) {
            check(node, graph[node]!!.toList())
        }

        ans = ans.sorted()
        println(ans.joinToString(","))

        return ans.size.toLong()
    }

}
