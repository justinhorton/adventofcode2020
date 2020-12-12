package xyz.justinhorton.aoc2020.solutions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Day12Test {
    private lateinit var day: Day12

    @BeforeEach
    fun beforeEach() {
        val inFile = Day12Test::class.java.getResource("input-day12.txt")
        day = Day12(inFile.readText())
    }

    @Test
    fun `test part 1 sample`() {
        assertEquals(25, Day12(SAMPLE).part1Solution.computeAnswer())
    }

    @Test
    fun `test part 1`() {
        assertEquals(2228, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2 sample`() {
        assertEquals(2144, Day11(SAMPLE).part2Solution.computeAnswer())
    }

    // this takes ~9.5 mins...
    @Test
    fun `test part 2`() {
        assertEquals(3, day.part2Solution.computeAnswer())
    }
}

private val SAMPLE = """
    F10
    N3
    F7
    R90
    F11
""".trimIndent()
