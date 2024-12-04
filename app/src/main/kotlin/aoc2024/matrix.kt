package aoc2024

import java.io.BufferedReader

typealias Element<T> = Triple<Int, Int, T>

open class Matrix<T>(reader: BufferedReader, converter: (Char) -> T): Iterator<Element<T>> {
    val rows: List<List<T>>
    val m: Int 
    val n: Int
    private var index = 0

    init {
        rows = reader.lineSequence()
            .map {
                it.toCharArray()
                    .map(converter)
            }
            .toList()

        m = rows.size
        n = if (m > 0) rows[0].size else 0
    }

    override fun hasNext(): Boolean {
        return index < m * n
    }

    override fun next(): Element<T> {
        val i = index / n
        val j = index % n
        index += 1
        return Element(i, j, rows[i][j])
    }

    fun get(i: Int, j: Int): T? {
        if (i in 0 until m && j in 0 until n) {
            return rows[i][j]
        }
        return null 
    }

    fun isAt(i: Int, j: Int, c: T): Boolean {
        return get(i, j) == c
    }
}

fun <T, R> Matrix<T>.map(transform: (Element<T>) -> R): List<R> {
    return this.iterator().asSequence().map(transform).toList()
}

fun <T> Matrix<T>.filter(predicate: (Element<T>) -> Boolean): List<Element<T>> {
    return (0 until m).asSequence()
        .map { i ->
            (0 until n).asSequence()
                .map{ j -> Element(i, j, get(i, j)!!) }
                .filter { predicate(it) }
                .toList()
        }
        .flatten()
        .toList()
}