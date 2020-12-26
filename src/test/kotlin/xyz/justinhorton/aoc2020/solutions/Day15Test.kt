package xyz.justinhorton.aoc2020.solutions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day15Test {
    private lateinit var day: Day15

    @BeforeEach
    fun beforeEach() {
        day = Day15()
    }

    @Test
    fun `test part 1 sample`() {
        assertEquals(436, Day15("0,3,6").part1Solution.computeAnswer())
    }

    @Test
    fun `test part 1`() {
        assertEquals(410, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2 sample`() {
        assertEquals(175594, Day15("0,3,6").part2Solution.computeAnswer())
    }

    @Test
    fun `test part 2`() {
        assertEquals(238, day.part2Solution.computeAnswer())
    }
}
