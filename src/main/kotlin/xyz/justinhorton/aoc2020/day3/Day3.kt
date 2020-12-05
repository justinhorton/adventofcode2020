package xyz.justinhorton.aoc2020.day3

import xyz.justinhorton.aoc2020.Day
import xyz.justinhorton.aoc2020.Solution

/**
 * [Advent of Code - Day 3](https://adventofcode.com/2020/day/3)
 */
class Day3(input: String) : Day<ReplicatingGrid, Int, Long> {
    override val parsedInput: ReplicatingGrid by lazy {
        val prototypeGrid: MutableList<List<Tile>> = mutableListOf()
        input.lineSequence()
            .filterNot { it.isBlank() }
            .forEach { line ->
                prototypeGrid.add(line.map { c -> c.asTile() })
            }
        ReplicatingGrid(prototypeGrid)
    }

    override val part1Solution: Solution<Int> = Solution {
        val grid = parsedInput
        val treesEncountered = grid.tilesSequence(deltaX = 3, deltaY = 1)
            .count { it == Tile.Blocked }
        treesEncountered
    }

    /**
     * Right 1, down 1.
     * Right 3, down 1. (This is the slope you already checked.)
     * Right 5, down 1.
     * Right 7, down 1.
     * Right 1, down 2.
     */
    override val part2Solution: Solution<Long> = Solution {
        val grid = parsedInput
        val xyDeltas = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)

        var product = 1L
        xyDeltas.forEach { (x, y) ->
            val treesEncountered = grid.tilesSequence(deltaX = x, deltaY = y)
                .count { it == Tile.Blocked }
            product *= treesEncountered
        }
        product
    }
}

class ReplicatingGrid(private val prototypeGrid: List<List<Tile>>) {
    val height = prototypeGrid.size
    val width = prototypeGrid.first().size

    fun tileAt(x: Int, y: Int): Tile {
        require(x >= 0 && y >= 0)
        return if (y >= height) {
            Tile.None
        } else {
            prototypeGrid[y][shiftX(x)]
        }
    }

    fun tilesSequence(startX: Int = 0, startY: Int = 0, deltaX: Int, deltaY: Int): Sequence<Tile> {
        return sequence {
            var curTile = tileAt(startX, startY)

            var curX = startX
            var curY = startY
            while (curTile != Tile.None) {
                curX += deltaX
                curY += deltaY

                curTile = tileAt(curX, curY)
                yield(curTile)
            }
        }
    }

    private fun shiftX(rawX: Int): Int = rawX % width
}

fun Char.asTile(): Tile = when (this) {
    '.' -> Tile.Open
    '#' -> Tile.Blocked
    else -> throw IllegalArgumentException("No known tile for '$this'")
}

enum class Tile {
    Open,
    Blocked,
    None,
    ;
}
