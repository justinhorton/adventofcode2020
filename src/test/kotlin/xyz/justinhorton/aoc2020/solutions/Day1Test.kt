package xyz.justinhorton.aoc2020.solutions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day1Test {
    private lateinit var day: Day1

    @BeforeEach
    fun beforeEach() {
        val inFile = Day1Test::class.java.getResource("input-day1.txt")
        day = Day1(inFile.readText())
    }

    @Test
    fun `test part 1`() {
        assertEquals(1013211, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2`() {
        assertEquals(13891280, day.part2Solution.computeAnswer())
    }
}
