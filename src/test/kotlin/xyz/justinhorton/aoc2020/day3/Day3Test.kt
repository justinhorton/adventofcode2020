package xyz.justinhorton.aoc2020.day3

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day3Test {
    private lateinit var day: Day3

    @BeforeEach
    fun beforeEach() {
        val inFile = Day3Test::class.java.getResource("input.txt")
        day = Day3(inFile.readText())
    }

    @Test
    fun `test sample 1 part 1`() {
        val day = Day3(SAMPLE_INPUT)
        assertEquals(7, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test sample 1 part 2`() {
        val day = Day3(SAMPLE_INPUT)
        assertEquals(336L, day.part2Solution.computeAnswer())
    }

    @Test
    fun `test part 1`() {
        assertEquals(173, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2`() {
        assertEquals(4385176320L, day.part2Solution.computeAnswer())
    }
}

private val SAMPLE_INPUT =
    """..##.........##.........##.........##.........##.........##.......
#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....
.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........#.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...##....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#
""".trim()
