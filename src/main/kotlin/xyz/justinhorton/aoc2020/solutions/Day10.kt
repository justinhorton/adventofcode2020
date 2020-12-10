package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution

/**
 * [Advent of Code - Day 10](https://adventofcode.com/2020/day/10)
 */
class Day10(input: String) : Day<List<Int>, Int, Long> {
    override val parsedInput: List<Int> by lazy {
        input.trim()
            .lineSequence()
            .map { it.toInt() }
            .sorted()
            .toList()
    }

    // charging outlet = 0 jolts
    // device = max adapter jolts + 3
    // adapter can connect to source 1-3 jolts lower than own rating
    override val part1Solution: Solution<Int> = Solution {
        val binnedJoltDiffs = mutableMapOf<Int, Int>()
        val joltages = parsedInput

        var priorJolts = 0
        joltages.forEach { adapterJolts ->
            binnedJoltDiffs.merge(adapterJolts - priorJolts, 1) { old, _ -> old + 1 }
            priorJolts = adapterJolts
        }

        binnedJoltDiffs.getOrDefault(1, 0) * (binnedJoltDiffs.getOrDefault(3, 0) + 1)
    }

    override val part2Solution: Solution<Long> = Solution {
        val joltages = parsedInput.toMutableList().apply {
            add(0, 0)
            add(parsedInput.last() + 3)
        }

        val deviceJoltage = joltages.last()

        // track unique nodes, sorted by ascending jolt value and hook them up to one another as a simple graph
        val graph = sortedMapOf<Int, Node>()
        joltages.mapIndexed { i, joltage ->
            val node: Node = graph.getOrPut(joltage) { Node(joltage) }

            val possibleNext = joltages.nextValuesWithin(graph, i, joltage, 3)
            node.next = possibleNext
            node.goal = graph.getOrPut(deviceJoltage) { Node(deviceJoltage) }
        }

        graph.values.first().numPathsToGoal

        // same as:
        // calcPathsWithMemo(graph.values.first(), graph.values.last(), mutableMapOf())
    }

    fun bruteForceNumPathsToGoal(startNode: Node, goalNode: Node): Long {
        return startNode.successors().filter { it === goalNode }.sumOf { 1L }
    }

    fun calcNumPathsToGoal(startNode: Node, goalNode: Node, memo: MutableMap<Node, Long>): Long {
        return if (startNode == goalNode) {
            1L
        } else {
            memo.getOrPut(startNode) {
                startNode.next.sumOf {
                    calcNumPathsToGoal(it, goalNode, memo)
                }
            }
        }
    }

    private fun List<Int>.nextValuesWithin(nodeMap: MutableMap<Int, Node>, index: Int, start: Int, within: Int): List<Node> {
        val one = getOrNull(index + 1)
        val two = getOrNull(index + 2)
        val three = getOrNull(index + 3)
        return listOfNotNull(one, two, three).asSequence()
            .filter { it - start <= within }
            .map { nodeMap.getOrPut(it) { Node(it) } }
            .toList()
    }
}

data class Node(val value: Int) {
    lateinit var next: List<Node>
    lateinit var goal: Node

    fun successors(): Sequence<Node> {
        return sequence {
            next.forEach {
                yield(it)
                yieldAll(it.successors())
            }
        }
    }

    /**
     * Lazy (with a captured [goal]) is insta-recursion-with-memoization. With K nodes, calling this on the start node
     * produces an initialization stack that is just K frames deep and, because the values are lazy, they memoize
     * themselves.
     */
    val numPathsToGoal: Long by lazy {
        if (this == goal) 1L else next.sumOf { n -> n.numPathsToGoal }
    }
}
