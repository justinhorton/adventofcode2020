package xyz.justinhorton.aoc2020.solutions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day16Test {
    private lateinit var day: Day16

    @BeforeEach
    fun beforeEach() {
        val inFile = Day16Test::class.java.getResource("input-day16.txt")
        day = Day16(inFile.readText())
    }

    @Test
    fun `test part 1`() {
        assertEquals(410, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2`() {
        assertEquals(238, day.part2Solution.computeAnswer())
    }
}
