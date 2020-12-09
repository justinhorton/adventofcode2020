package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution
import kotlin.collections.ArrayDeque

/**
 * [Advent of Code - Day 9](https://adventofcode.com/2020/day/9)
 */
class Day9(input: String, val lookbackLength: Int = 25) : Day<List<Long>, Long, Long> {
    override val parsedInput: List<Long> by lazy {
        input.trim().lineSequence()
            .map { it.toLong() }
            .toList()
    }

    override val part1Solution: Solution<Long> = Solution {
        val preamble: List<Long> = parsedInput.take(lookbackLength)
        val lookbackNums: ArrayDeque<Long> = ArrayDeque(preamble)

        parsedInput.drop(lookbackLength).first {
            val foundPair = lookbackNums.findPairSummingTo(it)
            lookbackNums.removeFirst()
            lookbackNums.addLast(it)
            foundPair == null
        }
    }

    private fun ArrayDeque<Long>.findPairSummingTo(sum: Long): Pair<Long, Long>? {
        val foundPair = firstOrNull {
            val other = sum - it
            it != other && contains(other)
        }
        return foundPair?.let { it to (sum - it) }
    }

    override val part2Solution: Solution<Long> = Solution {
        val nums = parsedInput

        val (rangeStart, rangeEnd) = nums.findIndicesOfContiguousRangeSummingTo(part1Solution.computeAnswer())
        val numRange = nums.subList(rangeStart, rangeEnd + 1)

        numRange.minOf { it } + numRange.maxOf { it }
    }

    private fun List<Long>.findIndicesOfContiguousRangeSummingTo(sum: Long): Pair<Int, Int> {
        for (startIndex in 0 until indexOf(sum)) {
            var runningSum = 0L
            for (i in startIndex until indexOf(sum)) {
                runningSum += this[i]
                if (runningSum == sum) {
                    return startIndex to i
                } else if (runningSum > sum) {
                    break
                }
            }
        }

        return -1 to -1
    }
}
