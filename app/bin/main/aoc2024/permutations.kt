package aoc2024

fun <T> MutableList<T>.swap(i: Int, j: Int) {
    val temp = this[i]
    this[i] = this[j]
    this[j] = temp
}

fun <T> List<T>.permutations(): Sequence<List<T>> = sequence {
    val input = this@permutations.toMutableList()
    permutationsRecursive(input, 0)
}

// Recursive function to generate permutations
private suspend fun <T> SequenceScope<List<T>>.permutationsRecursive(input: MutableList<T>, index: Int) {
    if (index == input.lastIndex) {
        yield(input.toList()) // Yield a copy of the current permutation
    } else {
        for (i in index..input.lastIndex) {
            input.swap(index, i) // Swap to create a new permutation
            permutationsRecursive(input, index + 1) // Recur with the next index
            input.swap(i, index) // Backtrack to restore the original order
        }
    }
}