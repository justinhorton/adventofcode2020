package xyz.justinhorton.aoc2020.solutions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Day13Test {
    private lateinit var day: Day13

    @BeforeEach
    fun beforeEach() {
        val inFile = Day13Test::class.java.getResource("input-day13.txt")
        day = Day13(inFile.readText(), 100000000000000L)
    }

    @Test
    fun `test part 1 sample`() {
        assertEquals(295, Day13(SAMPLE).part1Solution.computeAnswer())
    }

    @Test
    fun `test part 1`() {
        assertNotEquals(23767383291910, day.part1Solution.computeAnswer())
        assertEquals(171, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2 sample`() {
        assertEquals(1068781, Day13(SAMPLE).part2Solution.computeAnswer())
    }

    @Test
    fun `test part 2`() {
        assertEquals(539746751134958, day.part2Solution.computeAnswer())
    }
}

private val SAMPLE =
    """
    939
    7,13,x,x,59,x,31,19
    """.trimIndent()
