package xyz.justinhorton.aoc2020.solutions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day9Test {
    private lateinit var day: Day9

    @BeforeEach
    fun beforeEach() {
        val inFile = Day9Test::class.java.getResource("input-day9.txt")
        day = Day9(inFile.readText())
    }

    @Test
    fun `test part 1 sample`() {
        assertEquals(127, Day9(SAMPLE, lookbackLength = 5).part1Solution.computeAnswer())
    }

    @Test
    fun `test part 1`() {
        assertEquals(1212510616, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2 sample`() {
        assertEquals(62, Day9(SAMPLE, lookbackLength = 5).part2Solution.computeAnswer())
    }

    @Test
    fun `test part 2`() {
        assertEquals(171265123, day.part2Solution.computeAnswer())
    }
}

private val SAMPLE =
    """
    35
    20
    15
    25
    47
    40
    62
    55
    65
    95
    102
    117
    150
    182
    127
    219
    299
    277
    309
    576
    """.trimIndent()
