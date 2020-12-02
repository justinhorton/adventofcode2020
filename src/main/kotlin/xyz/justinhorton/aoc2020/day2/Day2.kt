package xyz.justinhorton.aoc2020.day2

import xyz.justinhorton.aoc2020.Day
import xyz.justinhorton.aoc2020.Solution
import java.util.Scanner
import java.util.regex.Pattern

/**
 * [Advent of Code - Day 2](https://adventofcode.com/2020/day/2)
 */
class Day2(private val inString: String) : Day<List<ConstrainedPassword>, Int, Int> {
    // 4-5 l: lkljtnpgvrfgwcmkj
    private val parsedInput: List<ConstrainedPassword> by lazy {
        inString.lineSequence()
            .filterNot { it.isBlank() }
            .map { line ->
                val scanner = Scanner(line).apply {
                    useDelimiter(Pattern.compile("(\\p{javaWhitespace}|-)+"))
                }

                ConstrainedPassword(
                    scanner.nextInt(),
                    scanner.nextInt(),
                    scanner.next()[0],
                    scanner.next()
                )
            }
            .toList()
    }

    override fun parseInput(inString: String): List<ConstrainedPassword> = parsedInput

    override val part1Solution: Solution<Int> = Solution {
        countOfInputsMeetingConstraint {
            pass.count { c -> c == constrainedLetter } in minOccur..maxOccur
        }
    }

    override val part2Solution: Solution<Int> = Solution {
        countOfInputsMeetingConstraint {
            // numbers are now 1-based indices into the pass
            val firstIndex = minOccur - 1
            val secondIndex = maxOccur - 1
            (pass[firstIndex] == constrainedLetter) xor (pass[secondIndex] == constrainedLetter)
        }
    }

    private fun countOfInputsMeetingConstraint(constraint: ConstrainedPassword.() -> Boolean): Int =
        parseInput(inString).asSequence()
            .filter(constraint)
            .count()
}

data class ConstrainedPassword(
    val minOccur: Int,
    val maxOccur: Int,
    val constrainedLetter: Char,
    val pass: String
)
