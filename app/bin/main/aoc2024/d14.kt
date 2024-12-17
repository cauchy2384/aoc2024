package aoc2024

import java.io.BufferedReader

class D14(val w: Long, val t: Long, val moves: Long = 100L): Solution<Long> {
    override val day = 14

    override fun part1(reader: BufferedReader): Long {
        return reader.lineSequence()
            .map{ it.split(" ") }
            .map{ it[0].substring(2).split(",").map{ it.toLong() } to it[1].substring(2).split(",").map{ it.toLong() } }
            .map{
                val (x, y) = it.first[0] to it.first[1]
                val (vx, vy) = it.second[0] to it.second[1]
                // println("$x $y $vx $vy")
                x + moves * vx to y + moves * vy
            }
            .map{ (x, y) -> (x % w + w) % w to (y % t + t) % t }
            .map{ (x, y) -> 
                when {
                    (x < w / 2 && y < t / 2) -> 0L
                    (x > w / 2 && y < t / 2) -> 1L
                    (x < w / 2 && y > t / 2) -> 2L
                    (x > w / 2 && y > t / 2) -> 3L
                    else -> -1L
                }
            }
            .filter{ it != -1L }
            .groupingBy{ it }
            .eachCount()
            .map{ it.value.toLong() }
            .fold(1L) { acc, i -> acc * i }
    }

    class Robot(val _x: Long, val _y: Long, val vx: Long, val vy: Long) {
        var x = _x
        var y = _y

        fun move(w: Long, t: Long) {
            x = (x + vx + w) % w 
            y = (y + vy + t) % t
        }
    }

    override fun part2(reader: BufferedReader): Long {
        val robots = reader.lineSequence()
            .map{ it.split(" ") }
            .map{ it[0].substring(2).split(",").map{ it.toLong() } to it[1].substring(2).split(",").map{ it.toLong() } }
            .map{ Robot(it.first[0], it.first[1], it.second[0], it.second[1]) }
            .toList()

        val (w, t) = w.toInt() to t.toInt()
        val mx = Array(t) { CharArray(w) { '.' } }
        (1 until 10/* 100000 */).map{ it ->
            robots.forEach{
                it.move(w.toLong(), t.toLong())
                mx[it.y.toInt()][it.x.toInt()] = '*'
            }
            var ok = false
            for (y in 0 until t) {
                for(x in 0 until w) {
                    ok = (0 until 6)
                        .map{
                            val x2 = (x + it + w) % w
                            val y2 = (y + it + t) % t
                            x2 in 0 until w && y2 in 0 until t && mx[y2][x2] == '*'
                        }
                        .all{ it }
                    if (ok) {
                        break
                    }
                }
                if (ok) {
                    break
                }
            }
            if (ok) {
                // for (i in 0 until t) {
                //     println(mx[i])
                // }
                // println("---------- STEP: ${it.toLong()}")
                // println("-------------------------------------------------------------------")
                // println()
                // println()
                // println()
            }
            robots.forEach{
                mx[it.y.toInt()][it.x.toInt()] = '.'
            }
            // println("--------------")
            // Thread.sleep(500) 
        }


        return 7492L
    }
}
