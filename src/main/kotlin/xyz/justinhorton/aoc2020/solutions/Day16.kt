package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution

private val CONSTRAINT_REGEX = "^([a-z\\s]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$".toRegex()

/**
 * [Advent of Code - Day 16](https://adventofcode.com/2020/day/16)
 */
class Day16(input: String) : Day<Day16.TicketInfo, Int, Long> {
    override val parsedInput: TicketInfo by lazy {
        val lines = input.trim().lines().filter { it.isNotEmpty() }

        val constraints = lines.takeWhile { it.matches(CONSTRAINT_REGEX) }.map {
            val match = CONSTRAINT_REGEX.matchEntire(it)!!
            val (name, range1Lower, range1Upper, range2Lower, range2Upper) = match.destructured

            TicketFieldConstraint(name, range1Lower.toInt()..range1Upper.toInt(), range2Lower.toInt()..range2Upper.toInt())
        }

        val yourTicketIndex = lines.indexOf("your ticket:") + 1
        val yourTicket = lines[yourTicketIndex].run {
            Ticket(split(",").map { it.toInt() })
        }

        val nearbyStart = lines.indexOf("nearby tickets:") + 1
        val nearbyTickets = lines.listIterator(nearbyStart).asSequence().map {
            Ticket(it.split(",").map { it.toInt() })
        }.toList()

        TicketInfo(constraints, yourTicket, nearbyTickets)
    }

    override val part1Solution: Solution<Int> = Solution {
        parsedInput.scanningErrorRate()
    }

    override val part2Solution: Solution<Long> = Solution {
        parsedInput.resolveTicketFields().filter { it.first.name.contains("departure") }.map { it.second }.fold(1) { acc, fieldNum ->
            acc * parsedInput.yourTicket.fields[fieldNum]
        }
    }

    class TicketInfo(private val constraints: List<TicketFieldConstraint>, val yourTicket: Ticket, private val nearbyTickets: List<Ticket>) {
        fun scanningErrorRate(): Int {
            return nearbyTickets.sumBy { nearbyTicket ->
                nearbyTicket.fields.sumBy { field ->
                    if (constraints.none { it.apply(field) }) {
                        field
                    } else {
                        0
                    }
                }
            }
        }

        private fun validNearbyTickets(): List<Ticket> = nearbyTickets.filterNot { it.isInvalid(constraints) }

        fun resolveTicketFields(): List<Pair<TicketFieldConstraint, Int>> {
            val validNearby = validNearbyTickets()
            val numFields = validNearby.first().fields.size

            // find field numbers that could be valid for each constraint
            val candidateFieldsByConstraint: MutableMap<TicketFieldConstraint, MutableList<Int>> = mutableMapOf()
            for (fieldNum in 0 until numFields) {
                constraints.forEach { constraint ->
                    if (validNearby.all { constraint.apply(it.fields[fieldNum]) }) {
                        candidateFieldsByConstraint.getOrPut(constraint) { mutableListOf() }.run { add(fieldNum) }
                    }
                }
            }

            val resolvedFields = mutableListOf<Pair<TicketFieldConstraint, Int>>()
            while (candidateFieldsByConstraint.any { it.value.size == 1 }) {
                // would-be fields with only one option can be resolved
                val resolvableFields = candidateFieldsByConstraint.filter { it.value.size == 1 }
                    .map { it.key to it.value.first() }
                resolvedFields.addAll(resolvableFields)

                // remove candidate entry for the resolved field's corresponding constraint
                resolvableFields.forEach { candidateFieldsByConstraint.remove(it.first) }
                // remove resolved field num from any other candidates
                resolvableFields.forEach { resolvedField ->
                    candidateFieldsByConstraint.values.forEach { possibleFieldNums ->
                        possibleFieldNums.remove(resolvedField.second)
                    }
                }
            }

            return resolvedFields
        }
    }

    class Ticket(val fields: List<Int>) {
        fun isInvalid(constraints: List<TicketFieldConstraint>): Boolean {
            return fields.any { field -> constraints.none { it.apply(field) } }
        }
    }

    data class TicketFieldConstraint(val name: String, val firstRange: IntRange, val secondRange: IntRange) {
        fun apply(value: Int): Boolean {
            return value in firstRange || value in secondRange
        }
    }
}

