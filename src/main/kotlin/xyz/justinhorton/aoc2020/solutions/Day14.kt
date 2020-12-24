package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution

/**
 * [Advent of Code - Day 14](https://adventofcode.com/2020/day/14)
 */
class Day14(input: String) : Day<List<Day14.Instruction>, Long, Long> {
    override val parsedInput: List<Instruction> by lazy {
        val memRegex = "mem\\[(\\d+)] = (\\d+)".toRegex()
        val maskRegex = "mask = ([01X]+)".toRegex()
        input.trim()
            .lineSequence()
            .map { line ->
                when {
                    line.matches(memRegex) -> {
                        val (dst, unmaskedValue) = memRegex.matchEntire(line)!!.destructured
                        MemStore(dst.toLong(), unmaskedValue.toLong())
                    }
                    line.matches(maskRegex) -> {
                        val bitString = maskRegex.matchEntire(line)!!.groupValues[1]
                        SetMask.from(bitString)
                    }
                    else -> {
                        throw IllegalArgumentException("Not matched")
                    }
                }
            }
            .toList()
    }

    override val part1Solution: Solution<Long> = Solution {
        val instructions = parsedInput
        val mem = mutableMapOf<Long, Long>()

        var maskInst: SetMask = instructions.first() as SetMask

        instructions.drop(1).forEach { instruction ->
            when (instruction) {
                is SetMask -> maskInst = instruction
                is MemStore -> {
                    val unmaskedValue = instruction.unmaskedValue
                    val valueToStore = (unmaskedValue and maskInst.andMask) or maskInst!!.orMask
                    mem[instruction.dst] = valueToStore
                }
            }
        }

        mem.values.sum()
    }

    fun Long.printBits() {
        for (i in 35 downTo 0) {
            print((this shr i) and 1L)
        }
    }

    override val part2Solution: Solution<Long>
        get() = TODO("Not yet implemented")

    interface Instruction
    class SetMask(val masks: List<BitMask>, val orMask: Long, val andMask: Long) : Instruction {
        companion object {
            fun from(bitString: String): SetMask {
                var pow = bitString.length - 1
                // mask on
                var orMask: Long = 0L
                // mask off
                var andMask: Long = (1L shl (pow + 1)) - 1


                val sbms = bitString.map { c ->
                    val mask = when (c) {
                        '1', '0' -> {
                            val isOne = c == '1'
                            if (isOne) {
                                orMask = orMask or (1L shl pow)
                            } else {
                                andMask = andMask xor (1L shl pow)
                            }
                            SingleBitMask(isOne)
                        }
                        else -> NoMask
                    }
                    pow -= 1
                    mask
                }
                return SetMask(sbms, orMask, andMask)
            }
        }
    }

    interface BitMask
    class SingleBitMask(val value: Boolean) : BitMask
    object NoMask : BitMask

    class MemStore(val dst: Long, val unmaskedValue: Long) : Instruction
}
