package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution
import kotlin.math.abs

/**
 * [Advent of Code - Day 12](https://adventofcode.com/2020/day/12)
 */
class Day12(input: String) : Day<List<Command>, Int, Int> {
    override val parsedInput: List<Command> by lazy {
        input.trim()
            .lineSequence()
            .map { line ->
                val typeChar = line.first()
                val value = line.substring(1).toInt()
                val type = when (typeChar) {
                    'N' -> Command.Type.North
                    'S' -> Command.Type.South
                    'E' -> Command.Type.East
                    'W' -> Command.Type.West
                    'L' -> Command.Type.Left
                    'R' -> Command.Type.Right
                    'F' -> Command.Type.Forward
                    else -> throw IllegalArgumentException("Bad type: $typeChar")
                }
                Command(type, value)
            }
            .toList()
    }

    override val part1Solution: Solution<Int> = Solution {
        val commands = parsedInput
        with(Ship()) {
            commands.forEach { move(it) }
            abs(xpos) + abs(ypos)
        }
    }

    override val part2Solution: Solution<Int> = Solution {
        val commands = parsedInput
        with(Ship()) {
            commands.forEach { moveWithWaypoint(it) }
            abs(xpos) + abs(ypos)
        }
    }
}

private class Ship(var xpos: Int = 0, var ypos: Int = 0, var bearing: Int = 0) {
    var wayx: Int = 10
    var wayy: Int = -1

    fun move(command: Command) {
        val v = command.value
        when (command.type) {
            Command.Type.North -> ypos -= v
            Command.Type.South -> ypos += v
            Command.Type.East -> xpos += v
            Command.Type.West -> xpos -= v
            Command.Type.Left -> {
                val b = bearing - v
                bearing = if (b < 0) 360 + b else b
            }
            Command.Type.Right -> {
                val b = bearing + v
                bearing = if (b >= 360) b - 360 else b
            }
            Command.Type.Forward -> {
                val type = when (bearing) {
                    0 -> Command.Type.East
                    90 -> Command.Type.South
                    180 -> Command.Type.West
                    270 -> Command.Type.North
                    else -> throw IllegalArgumentException("Unsupported bearing $bearing")
                }
                move(Command(type, v))
            }
        }
    }

    fun moveWithWaypoint(command: Command) {
        val v = command.value
        when (command.type) {
            Command.Type.North -> wayy -= v
            Command.Type.South -> wayy += v
            Command.Type.East -> wayx += v
            Command.Type.West -> wayx -= v
            Command.Type.Right -> {
                repeat(v / 90) {
                    val x = wayx
                    val y = wayy
                    wayx = -y
                    wayy = x
                }
            }
            Command.Type.Left -> {
                repeat(v / 90) {
                    val x = wayx
                    val y = wayy
                    wayx = y
                    wayy = -x
                }
            }
            Command.Type.Forward -> {
                xpos += wayx * v
                ypos += wayy * v
            }
        }
    }
}

class Command(val type: Type, val value: Int) {
    // ship starts facing East
    enum class Type {
        North,
        South,
        East,
        West,
        Left, // degrees, changes direction
        Right, // degrees, changes direction
        Forward, // move in current direction
    }
}
