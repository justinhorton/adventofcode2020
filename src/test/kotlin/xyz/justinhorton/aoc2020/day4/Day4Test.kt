package xyz.justinhorton.aoc2020.day4

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day4Test {
    private lateinit var day: Day4

    @BeforeEach
    fun beforeEach() {
        val inFile = Day4Test::class.java.getResource("input.txt")
        day = Day4(inFile.readText())
    }

    @Test
    fun `test sample part 1`() {
        val day = Day4(SAMPLE_INPUT)
        assertEquals(2, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test part 1`() {
        assertEquals(256, day.part1Solution.computeAnswer())
    }

    @Test
    fun `test valid passwords part 2`() {
        val day4 = Day4(SAMPLE_2_VALID)
        assertEquals(4, day4.part2Solution.computeAnswer())
    }

    @Test
    fun `test invalid passwords part 2`() {
        val day4 = Day4(SAMPLE_2_INVALID)
        assertEquals(0, day4.part2Solution.computeAnswer())
    }

    @Test
    fun `test part 2`() {
        assertEquals(198, day.part2Solution.computeAnswer())
    }
}

private val SAMPLE_INPUT =
    """ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648
iyr:2011 ecl:brn hgt:59in
""".trim()

private val SAMPLE_2_VALID =
    """
    pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
    hcl:#623a2f

    eyr:2029 ecl:blu cid:129 byr:1989
    iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

    hcl:#888785
    hgt:164cm byr:2001 iyr:2015 cid:88
    pid:545766238 ecl:hzl
    eyr:2022

    iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719
    """.trimIndent()

private val SAMPLE_2_INVALID =
    """
    eyr:1972 cid:100
    hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

    iyr:2019
    hcl:#602927 eyr:1967 hgt:170cm
    ecl:grn pid:012533040 byr:1946

    hcl:dab227 iyr:2012
    ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

    hgt:59cm ecl:zzz
    eyr:2038 hcl:74454a iyr:2023
    pid:3556412378 byr:2007
    """.trimIndent()
