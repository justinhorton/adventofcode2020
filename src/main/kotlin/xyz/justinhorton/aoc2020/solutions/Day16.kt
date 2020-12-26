package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution

private val CONSTRAINT_REGEX = "^([a-z\\s]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$".toRegex()
/**
 * [Advent of Code - Day 16](https://adventofcode.com/2020/day/16)
 */
class Day16(input: String) : Day<Day16.TicketInfo, Int, Int> {
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

    override val part2Solution: Solution<Int> = Solution {
        TODO()
    }

    class TicketInfo(val constraints: List<TicketFieldConstraint>, val yourTicket: Ticket, val nearbyTickets: List<Ticket>) {
        fun scanningErrorRate(): Int {
//            return nearbyTickets.flatMap { nearbyTicket -> nearbyTicket.fields }
//                .sumBy { field -> if (constraints.none { it.apply(field) } ) }
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
    }

    class Ticket(val fields: List<Int>)

    class TicketFieldConstraint(val name: String, val firstRange: IntRange, val secondRange: IntRange) {
        fun apply(value: Int): Boolean {
            return value in firstRange || value in secondRange
        }
    }
}

