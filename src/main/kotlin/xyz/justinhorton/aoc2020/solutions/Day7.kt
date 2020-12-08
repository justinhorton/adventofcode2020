package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution

private typealias BagRulesByType = Map<String, BagContentRule>
private const val TARGET_BAG_TYPE = "shiny gold"

/**
 * [Advent of Code - Day 7](https://adventofcode.com/2020/day/7)
 */
class Day7(input: String) : Day<BagRulesByType, Int, Int> {
    override val parsedInput: BagRulesByType by lazy {
        input.trim()
            .lineSequence()
            .map { line ->
                val ruleBag: String = "^(\\w+ \\w+) bags".toRegex().find(line)!!.groupValues[1]

                val contentRegex = "(\\d+) (\\w+ \\w+) bags?".toRegex()
                val contentDescriptors = contentRegex.findAll(line)
                val bagCounts = contentDescriptors.map { cd ->
                    val (count, bagId) = cd.destructured
                    AllowedBagContent(bagId, count.toInt())
                }.toList()

                BagContentRule(ruleBag, bagCounts)
            }
            .associateBy { it.bagType }
    }

    override val part1Solution: Solution<Int> = Solution {
        parsedInput.values.count { rule ->
            rule.canContainBagType(TARGET_BAG_TYPE, parsedInput::getValue)
        }
    }

    override val part2Solution: Solution<Int> = Solution {
        val shinyGoldRule = parsedInput.getValue(TARGET_BAG_TYPE)
        shinyGoldRule.countOfPossiblyContainedBags(parsedInput::getValue)
    }
}

class BagContentRule(val bagType: String, val allowedContents: List<AllowedBagContent>) {
    fun canContainBagType(desiredType: String, ruleLookup: (String) -> BagContentRule): Boolean {
        return allowedContents.any { desiredType == it.bagType } ||
            allowedContents.any { ruleLookup(it.bagType).canContainBagType(desiredType, ruleLookup) }
    }

    fun countOfPossiblyContainedBags(ruleLookup: (String) -> BagContentRule): Int {
        return allowedContents.sumOf { allowedBagContent ->
            val ruleForContent = ruleLookup(allowedBagContent.bagType)
            allowedBagContent.count + allowedBagContent.count * ruleForContent.countOfPossiblyContainedBags(ruleLookup)
        }
    }
}

class AllowedBagContent(val bagType: String, val count: Int)
