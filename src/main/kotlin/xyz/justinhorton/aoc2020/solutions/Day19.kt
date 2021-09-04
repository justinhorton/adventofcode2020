package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution

private typealias ParsedInputPair = Pair<List<Day19.Rule.Simple>, List<Day19.Message>>

/**
 * [Advent of Code - Day 19](https://adventofcode.com/2020/day/19)
 */
class Day19(input: String) : Day<ParsedInputPair, Int, Int> {
    override val parsedInput: ParsedInputPair by lazy {
        val split = input.trim().split("\n\n")
        val rulesToParse = split[0]
        val messagesToParse = split[1]

        val rules = rulesToParse.trim().lines().map { r ->
            val ruleIdAndRest = r.split(": ")
            val ruleId = ruleIdAndRest[0].toInt()
            val rest = ruleIdAndRest[1]

            rest.split(" ").let {
                Rule.Simple(ruleId, it)
            }
        }

        val messages = messagesToParse.trim().lines().map { Message(it) }
        rules to messages
    }

    override val part1Solution: Solution<Int> = Solution {
        val (rules, messages) = parsedInput

        val rulesMap: Map<Int, Rule.Simple> = rules.associateBy { it.ruleId }
        val ruleRegex = "^${expandRuleRegex(0, rulesMap)}$".toRegex()
        messages.count { it.str.matches(ruleRegex) }
    }

    override val part2Solution: Solution<Int> = Solution {
        val (rules, messages) = parsedInput

        val rulesMap: Map<Int, Rule.Simple> = rules.associateBy { it.ruleId }
        val ruleRegex = "^${expandRuleRegex(0, rulesMap, part2 = true)}$".toRegex()
        messages.count { it.str.matches(ruleRegex) }
    }

    /**
     * Part 2 changes rules to:
     * ```
     * 8: 42 | 42 8
     *      42 | 42 42 | 42 42 42 | 42 42 42 42 ...
     *      (42)+
     * 11: 42 31 | 42 11 31
     *      42 31 | 42 42 31 31 | 42 42 42 31 31 31 | 42 42 42 42 31 31 31 31 ...
     * ```
     *
     * were:
     * ```
     * 8: 42
     * 11: 42 31
     * ```
     */
    private fun expandRuleRegex(ruleId: Int, rulesMap: Map<Int, Rule.Simple>, part2: Boolean = false): String {
        val daRule = rulesMap.getValue(ruleId)

        if (part2) {
            if (daRule.ruleId == 8) {
                return "(${expandRuleRegex(42, rulesMap, part2)})+"
            }

            if (daRule.ruleId == 11) {
                val sb = StringBuilder(256)

                val rule42 = expandRuleRegex(42, rulesMap, part2)
                val rule31 = expandRuleRegex(31, rulesMap, part2)

                // manually expand out a few times (made up number that works)
                // and w/ the correct result found, is the minimum # of expansions
                sb.append('(')
                repeat(5) { i ->
                    if (i != 0) {
                        sb.append("|")
                    }
                    repeat(i + 1) { sb.append(rule42) }
                    repeat(i + 1) { sb.append(rule31) }
                }
                sb.append(')')

                return sb.toString()
            }
        }

        val sb = StringBuilder()

        sb.append("(")
        for (token in daRule.tokens) {
            when {
                token.first().isDigit() -> sb.append(expandRuleRegex(token.toInt(), rulesMap, part2))
                token.first() == '"' -> sb.append(token[1])
                else -> {
                    require(token == "|") { "token was $token" }
                    sb.append("|")
                }
            }
        }
        sb.append(")")

        return sb.toString()
    }

    sealed class Rule {
        abstract val ruleId: Int

        class Simple(override val ruleId: Int, val tokens: List<String>) : Day19.Rule()
    }

    class Message(val str: String)
}
