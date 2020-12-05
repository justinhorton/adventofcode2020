package xyz.justinhorton.aoc2020.day5

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day5Test {
    private lateinit var day: Day5

    @BeforeEach
    fun beforeEach() {
        val inFile = Day5Test::class.java.getResource("input.txt")
        day = Day5(inFile.readText())
    }

    @Test
    fun `test part 1`() {
        assertEquals(938, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2`() {
        assertEquals(696, day.part2Solution.computeAnswer())
    }
}
