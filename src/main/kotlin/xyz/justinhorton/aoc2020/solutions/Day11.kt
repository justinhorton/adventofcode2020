package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution

/**
 * [Advent of Code - Day 11](https://adventofcode.com/2020/day/11)
 */
class Day11(input: String) : Day<FloorGrid, Int, Int> {
    override val parsedInput: FloorGrid by lazy {
        val r: List<List<FloorTile>> = input.trim()
            .lineSequence()
            .map { line ->
                val tiles = line.map { c ->
                    when (c) {
                        '#' -> FloorTile.TakenSeat
                        'L' -> FloorTile.OpenSeat
                        '.' -> FloorTile.Floor
                        else -> throw IllegalArgumentException("Unknown tile character: $c")
                    }
                }
                tiles
            }.toList()
        FloorGrid(r.map { it.toTypedArray() }.toTypedArray())
    }

    override val part1Solution: Solution<Int> = Solution {
        val initialGrid = parsedInput

        var previousGrid: FloorGrid
        var nextGrid = initialGrid
        do {
            previousGrid = nextGrid
            nextGrid = previousGrid.asNextGrid()
        } while (previousGrid != nextGrid)

        val stableGrid: FloorGrid = nextGrid
        stableGrid.tileSequence().count { t -> t == FloorTile.TakenSeat }
    }

    override val part2Solution: Solution<Int> = Solution {
        val initialGrid = parsedInput

        var previousGrid: FloorGrid
        var nextGrid = initialGrid
        do {
            previousGrid = nextGrid
            nextGrid = previousGrid.asRevisedNextGrid()
        } while (previousGrid != nextGrid)

        val stableGrid = nextGrid
        stableGrid.tileSequence().count { t -> t == FloorTile.TakenSeat }
    }
}

enum class FloorTile(val char: Char) {
    OpenSeat('L'),
    TakenSeat('#'),
    Floor('.'),
    None('*'),
}

data class FloorGrid(val tiles: Array<Array<FloorTile>>) {
    private fun tileAt(x: Int, y: Int): FloorTile {
        return (tiles.getOrElse(y) { emptyArray() }).getOrElse(x) { FloorTile.None }
    }

    fun tileSequence(): Sequence<FloorTile> = sequence {
        tiles.forEach { yieldAll(it.iterator()) }
    }

    /* Part 1 */

    fun asNextGrid(): FloorGrid {
        val nextArr = Array(tiles.size) { row -> tiles[row].copyOf() }
        tiles.indices.forEach { y ->
            tiles[y].indices.forEach { x ->
                nextArr[y][x] = nextTileAt(x, y)
            }
        }
        return FloorGrid(nextArr)
    }

    private fun nextTileAt(x: Int, y: Int): FloorTile {
        return when (val curTile = tileAt(x, y)) {
            FloorTile.OpenSeat -> {
                if (takenAdjacentCount(x, y) == 0) {
                    FloorTile.TakenSeat
                } else {
                    curTile
                }
            }
            FloorTile.TakenSeat -> {
                if (takenAdjacentCount(x, y) >= 4) {
                    FloorTile.OpenSeat
                } else {
                    curTile
                }
            }
            else -> curTile
        }
    }

    private fun takenAdjacentCount(x: Int, y: Int): Int {
        var takenCount = 0
        onEachAdjacentTile(x, y) { if (it === FloorTile.TakenSeat) takenCount += 1 }
        return takenCount
    }

    private inline fun onEachAdjacentTile(x: Int, y: Int, action: (FloorTile) -> Unit) {
        action(tileAt(x - 1, y))
        action(tileAt(x - 1, y - 1))
        action(tileAt(x - 1, y + 1))
        action(tileAt(x + 1, y))
        action(tileAt(x + 1, y - 1))
        action(tileAt(x + 1, y + 1))
        action(tileAt(x, y - 1))
        action(tileAt(x, y + 1))
    }

    /* Part 2 */

    fun asRevisedNextGrid(): FloorGrid {
        val nextArr = Array(tiles.size) { row -> tiles[row].copyOf() }
        tiles.indices.forEach { y ->
            tiles[y].indices.forEach { x ->
                nextArr[y][x] = revisedNextTileAt(x, y)
            }
        }
        return FloorGrid(nextArr)
    }

    fun revisedNextTileAt(x: Int, y: Int): FloorTile {
        return when (val curTile = tileAt(x, y)) {
            FloorTile.OpenSeat -> {
                var taken = 0
                onEachVisibleTile(x, y) { if (it == FloorTile.TakenSeat) taken++ }

                if (taken == 0) {
                    FloorTile.TakenSeat
                } else {
                    curTile
                }
            }
            FloorTile.TakenSeat -> {
                var taken = 0
                onEachVisibleTile(x, y) { if (it == FloorTile.TakenSeat) taken++ }

                if (taken >= 5) {
                    FloorTile.OpenSeat
                } else {
                    curTile
                }
            }
            else -> curTile
        }
    }

    private inline fun onEachVisibleTile(x: Int, y: Int, onEach: (FloorTile) -> Unit) {
        DELTAS.forEach { (xd, yd) ->
            var curx = x
            var cury = y
            var foundFirst = false
            do {
                curx += xd
                cury += yd

                val t = tileAt(curx, cury)
                if (t == FloorTile.TakenSeat || t == FloorTile.OpenSeat) {
                    onEach(t)
                    foundFirst = true
                }
            } while (!foundFirst && t != FloorTile.None)
        }
    }

    fun print() {
        tiles.forEach { row ->
            row.forEach { t ->
                print(t.char)
            }
            println()
        }
    }

    override fun equals(other: Any?): Boolean = other is FloorGrid && tiles contentDeepEquals other.tiles

    override fun hashCode(): Int = tiles.contentDeepHashCode()

    companion object {
        private val DELTAS = listOf(-1 to -1, -1 to 0, -1 to 1, 1 to -1, 1 to 0, 1 to 1, 0 to -1, 0 to 1)
    }
}
