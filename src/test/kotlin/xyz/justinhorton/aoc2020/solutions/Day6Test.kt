package xyz.justinhorton.aoc2020.solutions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day6Test {
    private lateinit var day: Day6

    @BeforeEach
    fun beforeEach() {
        val inFile = Day6Test::class.java.getResource("input-day6.txt")
        day = Day6(inFile.readText())
    }

    @Test
    fun `test part 1`() {
        assertEquals(6335, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2`() {
        assertEquals(3392, day.part2Solution.computeAnswer())
    }
}
