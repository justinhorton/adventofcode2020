package xyz.justinhorton.aoc2020.day2

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day2Test {
    private lateinit var day: Day2

    @BeforeEach
    fun beforeEach() {
        val inFile = Day2Test::class.java.getResource("input.txt")
        day = Day2(inFile.readText())
    }

    @Test
    fun `test part 1`() {
        assertEquals(383, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2`() {
        assertEquals(272, day.part2Solution.computeAnswer())
    }
}
