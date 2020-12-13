package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.roundToInt

private typealias BusId = Int
private typealias Timestamp = Int

/**
 * [Advent of Code - Day 13](https://adventofcode.com/2020/day/13)
 */
class Day13(input: String) : Day<Pair<Timestamp, List<BusId>>, Int, Int> {
    override val parsedInput: Pair<Int, List<Int>> by lazy {
        val lines = input.trim().lines()
        val earliestTimestamp = lines[0].toInt()

        val busIds = lines[1].split(",").mapNotNull {
            it.toIntOrNull()
        }
        Pair(earliestTimestamp, busIds)
    }

    override val part1Solution: Solution<Int> = Solution {
        val (earliestTimestamp, busIds) = parsedInput

        val result: List<Pair<Int, BusId>> = busIds.asSequence()
            .map { id ->
                val earliestDeparture = ceil(earliestTimestamp / id.toDouble()).toInt() * id
                (earliestDeparture - earliestTimestamp) to id
            }
            .sortedBy { it.first }
            .toList()

        val r = result.first()
        r.second * r.first
    }

    override val part2Solution: Solution<Int> = Solution {
        TODO()
    }
}


