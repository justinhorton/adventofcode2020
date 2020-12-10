package xyz.justinhorton.aoc2020.solutions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day10Test {
    private lateinit var day: Day10

    @BeforeEach
    fun beforeEach() {
        val inFile = Day10Test::class.java.getResource("input-day10.txt")
        day = Day10(inFile.readText())
    }

    @Test
    fun `test part 1 sample`() {
        assertEquals(7 * 5, Day10(SMALL_SAMPLE).part1Solution.computeAnswer())
    }

    @Test
    fun `test part 1 large sample`() {
        assertEquals(22 * 10, Day10(LARGE_SAMPLE).part1Solution.computeAnswer())
    }

    @Test
    fun `test part 1`() {
        assertEquals(2450, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 2 large sample`() {
        assertEquals(19208, Day10(LARGE_SAMPLE).part2Solution.computeAnswer())
    }

    @Test
    fun `test part 2 small sample`() {
        assertEquals(8, Day10(SMALL_SAMPLE).part2Solution.computeAnswer())
    }

    @Test
    fun `test part 2`() {
        assertEquals(32396521357312, day.part2Solution.computeAnswer())
    }
}

private val SMALL_SAMPLE =
    """
    16
    10
    15
    5
    1
    11
    7
    19
    6
    12
    4
    """.trimIndent()

private val LARGE_SAMPLE =
    """
    28
    33
    18
    42
    31
    14
    46
    20
    48
    47
    24
    23
    49
    45
    19
    38
    39
    11
    1
    32
    25
    35
    8
    17
    7
    9
    4
    2
    34
    10
    3
    """.trimIndent()
