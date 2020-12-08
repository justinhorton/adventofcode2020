package xyz.justinhorton.aoc2020.solutions

import xyz.justinhorton.aoc2020.template.Day
import xyz.justinhorton.aoc2020.template.Solution

/**
 * [Advent of Code - Day 8](https://adventofcode.com/2020/day/8)
 */
class Day8(input: String) : Day<Program, Int, Int> {
    override val parsedInput: Program by lazy {
        val regex = "^(\\w+) (([+\\-])\\d+)$".toRegex()
        val instructions = input.trim()
            .lineSequence()
            .map {
                val match = regex.matchEntire(it)
                val (opcode, arg) = match!!.destructured

                val intArg = arg.toInt()
                when (opcode) {
                    "acc" -> Instruction.Acc(intArg)
                    "jmp" -> Instruction.Jmp(intArg)
                    "nop" -> Instruction.Nop(intArg)
                    else -> throw IllegalArgumentException("Unknown opcode $opcode")
                }
            }.toList()
        Program(instructions)
    }

    override val part1Solution: Solution<Int> = Solution {
        val program = parsedInput
        program.run()
        program.getAccumulatorValue()
    }

    override val part2Solution: Solution<Int> = Solution {
        val program = parsedInput
        val goodProgram = program.computeAlternativePrograms().first { it.run() == ProgramResult.Terminated }
        goodProgram.getAccumulatorValue()
    }
}

sealed class Instruction(val arg: Int) {
    class Acc(arg: Int) : Instruction(arg)
    class Jmp(arg: Int) : Instruction(arg)
    class Nop(arg: Int) : Instruction(arg)
}

enum class ProgramResult {
    InfiniteLoop,
    Terminated,
}

fun Program.computeAlternativePrograms(): Sequence<Program> = sequence {
    instructions.forEachIndexed { i, instruction ->
        instruction.corruptionReplacement()?.let { replacement ->
            yield(Program(instructions.toMutableList().apply { set(i, replacement) }))
        }
    }
}

fun Instruction.corruptionReplacement(): Instruction? = when (this) {
    is Instruction.Nop -> Instruction.Jmp(arg)
    is Instruction.Jmp -> Instruction.Nop(arg)
    else -> null
}

class Program(val instructions: List<Instruction>) {
    private var accumulator: Int = 0
    private var pc: Int = 0
    private var ranInstructions: MutableSet<Int> = hashSetOf()

    fun run(): ProgramResult {
        while (true) {
            val curInstruction: Instruction = instructions.getOrNull(pc)
                ?: return ProgramResult.Terminated

            if (!ranInstructions.add(pc)) {
                return ProgramResult.InfiniteLoop
            } else {
                when (curInstruction) {
                    is Instruction.Acc -> accumulator += curInstruction.arg
                    is Instruction.Jmp -> {
                        pc += curInstruction.arg
                        continue
                    }
                    is Instruction.Nop -> {}
                }

                pc++
            }
        }
    }

    fun getAccumulatorValue(): Int = accumulator
}
