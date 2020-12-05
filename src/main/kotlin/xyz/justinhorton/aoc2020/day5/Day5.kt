package xyz.justinhorton.aoc2020.day5

import xyz.justinhorton.aoc2020.Day
import xyz.justinhorton.aoc2020.Solution

/**
 * [Advent of Code - Day 5](https://adventofcode.com/2020/day/5)
 */
class Day5(input: String) : Day<List<String>, Int, Int> {

    override val parsedInput: List<String> by lazy {
        input.lineSequence()
            .filterNot { it.isBlank() }
            .toList()
    }

    // FBFBBFFRLR
    // F, L = lower half
    // B, R = upper half
    override val part1Solution: Solution<Int> = Solution {
        parsedInput.maxOf { calcSeatId(it) }
    }

    private fun calcSeatId(input: String): Int {
        val (row, col) = findSeatRowAndCol2(input)
        return 8 * row + col
    }

    private fun findSeatRowAndCol2(input: String): Pair<Int, Int> {
        val rowPart = input.substring(0..6)
            .replace('F', '0')
            .replace('B', '1')

        val colPart = input.substring(7..9)
            .replace('L', '0')
            .replace('R', '1')

        return rowPart.toInt(2) to colPart.toInt(2)
    }

    override val part2Solution: Solution<Int> = Solution {
        val maxId = part1Solution.computeAnswer()
        val seatIds = parsedInput.map { calcSeatId(it) }
        val minId = seatIds.minOf { it }
        val idGap = (minId..maxId) - seatIds
        idGap.first()
    }
}
