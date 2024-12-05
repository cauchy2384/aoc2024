package aoc2024

fun main() {
    val app = D05()
    
    object {}.javaClass.getResourceAsStream("../test/resources/d05/example.txt")?.use { stream ->
        stream.bufferedReader().use { reader ->
            val actual = app.part1(reader)
            println("Actual: $actual")
        }
    } ?: throw IllegalArgumentException("File not found")
}