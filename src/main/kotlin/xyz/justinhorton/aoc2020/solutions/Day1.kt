package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution

/**
 * [Advent of Code - Day 1](https://adventofcode.com/2020/day/1)
 */
class Day1(private val inString: String) : Day<List<Int>, Int, Int> {
    override val parsedInput: List<Int> by lazy {
        inString.lineSequence()
            .filterNot { it.isBlank() }
            .map { it.toInt() }
            .toList()
    }

    override val part1Solution: Solution<Int> = Solution {
        val pair = parsedInput.asSequence()
            .withIndex()
            .flatMap { (index, value) ->
                parsedInput.sequenceAfter(index).map { value to it }
            }
            .first { it.first + it.second == 2020 }

        pair.first * pair.second
    }

    override val part2Solution: Solution<Int> = Solution {
        val triple = parsedInput.asSequence()
            .withIndex()
            .flatMap { (firstIndex, firstValue) ->
                val rem = parsedInput.sequenceAfter(firstIndex).toList()

                rem.asSequence()
                    .withIndex()
                    .flatMap { (midIndex, midValue) ->
                        rem.sequenceAfter(midIndex).map { lastValue -> Triple(firstValue, midValue, lastValue) }
                    }
            }
            .first { it.first + it.second + it.third == 2020 }

        triple.first * triple.second * triple.third
    }
}

private fun <T> List<T>.sequenceAfter(index: Int): Sequence<T> {
    return if (index >= size) emptySequence() else listIterator(index).asSequence()
}
