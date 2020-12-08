package xyz.justinhorton.aoc2020.solutions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day8Test {
    private lateinit var day: Day8

    @BeforeEach
    fun beforeEach() {
        val inFile = Day8Test::class.java.getResource("input-day8.txt")
        day = Day8(inFile.readText())
    }

    @Test
    fun `test part 1 sample`() {
        assertEquals(5, Day8(SAMPLE).part1Solution.computeAnswer())
    }

    @Test
    fun `test part 1`() {
        assertEquals(1087, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2`() {
        assertEquals(780, day.part2Solution.computeAnswer())
    }
}

private val SAMPLE =
    """
    nop +0
    acc +1
    jmp +4
    acc +3
    jmp -3
    acc -99
    acc +1
    jmp -4
    acc +6
    """.trimIndent()
