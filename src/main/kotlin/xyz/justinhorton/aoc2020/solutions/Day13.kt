package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution
import kotlin.math.ceil

private typealias BusId = Int
private typealias Timestamp = Int

/**
 * [Advent of Code - Day 13](https://adventofcode.com/2020/day/13)
 */
class Day13(input: String, val minT: Long = 0L) : Day<Pair<Timestamp, List<Day13.BusConstraint>>, Int, Long> {
    override val parsedInput: Pair<Int, List<BusConstraint>> by lazy {
        val lines = input.trim().lines()
        val earliestTimestamp = lines[0].toInt()

        val busConstraints: List<BusConstraint> = lines[1].split(",").map {
            val busId: BusId? = it.toIntOrNull()
            busId?.let { parsedId -> BusIdConstraint(parsedId) } ?: AnyBusConstraint
        }
        Pair(earliestTimestamp, busConstraints)
    }

    override val part1Solution: Solution<Int> = Solution {
        val (earliestTimestamp, busIds) = parsedInput

        val earliestDepartureAndBusId: Pair<Int, BusId> = busIds.asSequence()
            .filterIsInstance<BusIdConstraint>()
            .map { it.busId }
            .map { id ->
                val earliestDeparture = ceil(earliestTimestamp / id.toDouble()).toInt() * id
                (earliestDeparture - earliestTimestamp) to id
            }
            .sortedBy { it.first }
            .first()

        val (timestamp, busId) = earliestDepartureAndBusId
        timestamp * busId
    }

    /**
     * [https://en.wikipedia.org/wiki/Chinese_remainder_theorem](Chinese Remainder Theorem)
     */
    override val part2Solution: Solution<Long> = Solution {
        val withOffsets: Array<BusInputOffsetConstraint> = parsedInput.second.mapIndexedNotNull { offset, busConstraint ->
            (busConstraint as? BusIdConstraint)?.let { BusInputOffsetConstraint(it.busId, offset) }
        }.toTypedArray()

        var solution = 0L
        var lcd = 1L
        withOffsets.forEach { bc ->
            while ((solution + bc.offset) % bc.busId != 0L) {
                solution += lcd
            }

            lcd *= bc.busId
        }
        solution
    }

    interface BusConstraint

    data class BusIdConstraint(val busId: BusId) : BusConstraint

    data class BusInputOffsetConstraint(val busId: BusId, val offset: Int) : BusConstraint {
        fun departsAt(t: Long): Boolean = t % busId == 0L

        fun nextDepartureAfter(t: Long): Long = ceil(t / busId.toDouble()).toLong() * busId
    }

    object AnyBusConstraint : BusConstraint
}
