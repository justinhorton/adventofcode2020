package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution

private const val INPUT: String = "7,12,1,0,16,2"

private typealias SpokenInt = Int
private typealias Turn = Int
/**
 * [Advent of Code - Day 15](https://adventofcode.com/2020/day/15)
 */
class Day15(input: String = INPUT) : Day<List<Int>, Int, Int> {
    override val parsedInput: List<Int> by lazy {
        input.split(",")
            .map { it.toInt() }
            .toList()
    }

    override val part1Solution: Solution<Int> = Solution {
        generateSequence(parsedInput).elementAt(2020 - 1)
    }

    override val part2Solution: Solution<Int> = Solution {
        generateSequence(parsedInput).elementAt(30_000_000 - 1)
    }

    private fun generateSequence(initialNumbers: List<Int>): Sequence<SpokenInt> {
        val spokenToTurn: MutableMap<SpokenInt, Turn> = mutableMapOf()

        var curTurn: Turn = 0
        parsedInput.dropLast(1).forEach { initial: SpokenInt ->
            spokenToTurn[initial] = ++curTurn
        }

        var lastSpoken = parsedInput.last()
        return initialNumbers.asSequence() + generateSequence {
            curTurn++

            val spokenOnTurn = spokenToTurn[lastSpoken]
            val nextNumber = if (spokenOnTurn == null) {
                0
            } else {
                curTurn - spokenOnTurn
            }

            spokenToTurn[lastSpoken] = curTurn
            lastSpoken = nextNumber

            nextNumber
        }
    }
}
