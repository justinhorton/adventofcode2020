package xyz.justinhorton.aoc2020.solutions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14Test {
    private lateinit var day: Day14

    @BeforeEach
    fun beforeEach() {
        val inFile = Day14Test::class.java.getResource("input-day14.txt")
        day = Day14(inFile.readText())
    }

    @Test
    fun `test part 1 sample`() {
        assertEquals(165, Day14(SAMPLE).part1Solution.computeAnswer())
    }

    @Test
    fun `test part 1`() {
        assertEquals(11884151942312, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2 sample`() {
    }

    @Test
    fun `test part 2`() {
    }
}

private val SAMPLE =
    """
    mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
    mem[8] = 11
    mem[7] = 101
    mem[8] = 0
    """.trimIndent()
