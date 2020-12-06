package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution

/**
 * [Advent of Code - Day 6](https://adventofcode.com/2020/day/6)
 */
class Day6(input: String) : Day<List<GroupAnswers>, Int, Int> {
    override val parsedInput: List<GroupAnswers> by lazy {
        input.trim()
            .splitToSequence("\n\n")
            .map { groupInputSection ->
                val splitInputsForGroup = groupInputSection.split("\n")

                val inputForFirstPerson = splitInputsForGroup.first()
                val anyAnswered: MutableSet<Char> = inputForFirstPerson.asSequence().toMutableSet()
                val allAnswered: MutableSet<Char> = anyAnswered.toMutableSet()

                splitInputsForGroup.drop(1).forEach { personInput ->
                    val answeredByPerson = personInput.toList()
                    allAnswered.retainAll(answeredByPerson)
                    anyAnswered.addAll(answeredByPerson)
                }

                GroupAnswers(anyAnswered, allAnswered)
            }
            .toList()
    }

    override val part1Solution: Solution<Int> = Solution {
        parsedInput.sumBy { it.anyAnswered.size }
    }

    override val part2Solution: Solution<Int> = Solution {
        parsedInput.sumBy { it.allAnswered.size }
    }
}

class GroupAnswers(val anyAnswered: Set<Char>, val allAnswered: Set<Char>)
