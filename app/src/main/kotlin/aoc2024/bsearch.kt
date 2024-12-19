package aoc2024

fun binarySearchFirstOccurrence(size: Int, cmp: (Int) -> Long, target: Long): Int {
    var low = 0
    var high = size - 1
    var result = -1

    while (low <= high) {
        val mid = low + (high - low) / 2
        when {
            cmp(mid) == target -> {
                result = mid
                high = mid - 1 // Continue searching in the lower half
            }
            cmp(mid) < target -> low = mid + 1
            else -> high = mid - 1
        }
    }

    return result
}