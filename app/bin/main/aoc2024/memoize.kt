package aoc2024

class Memoize<in T, out R>(val f: (T) -> R) : (T) -> R {
    private val cache = mutableMapOf<T, R>()

    override fun invoke(x: T): R {
        return cache.getOrPut(x) { f(x) }
    }
}

fun <T, R> ((T) -> R).memoize(): (T) -> R = Memoize(this)