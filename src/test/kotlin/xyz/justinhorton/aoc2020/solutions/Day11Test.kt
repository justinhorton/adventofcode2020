package xyz.justinhorton.aoc2020.solutions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    private lateinit var day: Day11

    @BeforeEach
    fun beforeEach() {
        val inFile = Day11Test::class.java.getResource("input-day11.txt")
        day = Day11(inFile.readText())
    }

    @Test
    fun `test part 1 sample`() {
        assertEquals(37, Day11(SAMPLE).part1Solution.computeAnswer())
    }

    @Test
    fun `test part 1`() {
        assertEquals(2418, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2 sample`() {
        assertEquals(26, Day11(SAMPLE).part2Solution.computeAnswer())
    }

    @Test
    fun `test part 2`() {
        assertEquals(2144, day.part2Solution.computeAnswer())
    }
}

private val SAMPLE =
    """
    L.LL.LL.LL
    LLLLLLL.LL
    L.L.L..L..
    LLLL.LL.LL
    L.LL.LL.LL
    L.LLLLL.LL
    ..L.L.....
    LLLLLLLLLL
    L.LLLLLL.L
    L.LLLLL.LL
    """.trimIndent()
