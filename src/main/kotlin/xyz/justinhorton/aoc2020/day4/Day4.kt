package xyz.justinhorton.aoc2020.day4

import xyz.justinhorton.aoc2020.Day
import xyz.justinhorton.aoc2020.Solution
import java.util.EnumSet

/**
 * [Advent of Code - Day 4](https://adventofcode.com/2020/day4)
 */
class Day4(input: String) : Day<List<Passport>, Int, Int> {
    override val parsedInput: List<Passport> by lazy {
        input.trim()
            .split("\n\n")
            .filterNot { it.isBlank() }
            .map { batchEntry ->
                val fields = batchEntry.split(" ", "\n")
                Passport(
                    fields.associate { f ->
                        val keyValue = f.split(":")
                        keyValue[0] to keyValue[1]
                    }
                )
            }.toList()
    }

    override val part1Solution: Solution<Int> = Solution {
        parsedInput.count { it.hasRequiredFields() }
    }

    override val part2Solution: Solution<Int> = Solution {
        parsedInput.count { it.validate() }
    }
}

private val REQUIRED_FIELDS = EnumSet.complementOf(EnumSet.of(Passport.Field.Cid))

// byr (Birth Year)
// iyr (Issue Year)
// eyr (Expiration Year)
// hgt (Height)
// hcl (Hair Color)
// ecl (Eye Color)
// pid (Passport ID)
// cid (Country ID)
//
// byr (Birth Year) - four digits; at least 1920 and at most 2002.
// iyr (Issue Year) - four digits; at least 2010 and at most 2020.
// eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
// hgt (Height) - a number followed by either cm or in:
// If cm, the number must be at least 150 and at most 193.
// If in, the number must be at least 59 and at most 76.
// hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
// ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
// pid (Passport ID) - a nine-digit number, including leading zeroes.
// cid (Country ID) - ignored, missing or not.

class Passport(data: Map<String, String>) {
    private val fields: Map<Field, String> = data.entries.associate { Field.valueOf(it.key.capitalize()) to it.value }

    fun hasRequiredFields(): Boolean = fields.keys.containsAll(REQUIRED_FIELDS)

    fun validate(): Boolean = hasRequiredFields() && fields.all {
        it.key.validate(it.value)
    }

    enum class Field {
        Byr,
        Iyr,
        Eyr,
        Hgt,
        Hcl,
        Ecl,
        Pid,
        Cid,
        ;

        fun validate(value: String): Boolean = when (this) {
            Byr -> value.isIntInRange(1920..2002)
            Iyr -> value.isIntInRange(2010..2020)
            Eyr -> value.isIntInRange(2020..2030)
            Hgt -> {
                val regex = "^(?<measurement>\\d+)(?<unit>(cm)|(in))$".toRegex()
                val match = regex.matchEntire(value)
                match?.let {
                    val measurement = it.groups["measurement"]!!.value
                    when (it.groups["unit"]!!.value) {
                        "cm" -> measurement.isIntInRange(150..193)
                        "in" -> measurement.isIntInRange(59..76)
                        else -> false
                    }
                } ?: false
            }
            Hcl -> {
                val regex = "^#([0-9]|[a-f]){6}$".toRegex()
                regex.matches(value)
            }
            Ecl -> {
                val allowed = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                allowed.contains(value)
            }
            Pid -> {
                val regex = "^\\d{9}$".toRegex()
                regex.matches(value)
            }
            Cid -> true
        }
    }
}

fun String.isIntInRange(intRange: IntRange): Boolean {
    return toIntOrNull()?.let { it in intRange } ?: false
}
