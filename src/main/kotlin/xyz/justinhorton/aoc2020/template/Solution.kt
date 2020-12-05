package xyz.justinhorton.aoc2020.template

fun interface Solution<R> {
    fun computeAnswer(): R
}

interface Day<I, R1, R2> {
    val parsedInput: I

    val part1Solution: Solution<R1>

    val part2Solution: Solution<R2>
}
