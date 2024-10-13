package com.gordwilling.sudoku

import com.gordwilling.sudoku.Difficulty.*
import kotlin.random.Random

enum class Difficulty {
    Easy, Medium, Hard, Expert
}

object SudokuMaker {

    private val values = initialize()

    fun create(difficulty: Difficulty): List<List<Int>> {
        return applyDifficulty(difficulty)
    }

    private fun initialize(): List<MutableList<Int>> {
        val allZeroes = allZeroes()
        (0..8).shuffled().forEach { row ->
            (0..8).shuffled().forEachIndexed { value, column ->
                allZeroes.maybeInsert(row, column, value + 1)
            }
        }
        return allZeroes.toList()
    }

    private fun List<MutableList<Int>>.maybeInsert(row: Int, column: Int, value: Int) {
        if (!illegalValues(row, column).contains(value)) {
            this[row][column] = value
        }
    }

    private fun List<MutableList<Int>>.illegalValues(row: Int, column: Int): Set<Int> {
        fun box(row: Int, column: Int): List<Int> {
            val topRow = row / 3
            val leftColumn = column / 3
            return (0..2).map { r ->
                (0..2).map { c ->
                    this[topRow + r][leftColumn + c]
                }
            }.flatten()
        }

        val rowValues = (0..8).map { c -> this[row][c] }
        val columnValues = (0..8).map { r -> this[r][column] }
        val boxValues = box(row, column)
        return (rowValues + columnValues + boxValues).toSet()
    }

    private fun applyDifficulty(difficulty: Difficulty): List<List<Int>> {
        val probabilyOfRemoval = when (difficulty) {
            Easy -> 0.8
            Medium -> 0.75
            Hard -> 0.7
            Expert -> 0.65
        }
        val adjustedValues = values.copy()
        (0..8).forEach { row ->
            (0..8).forEach { column ->
                if (Random.nextDouble() * probabilyOfRemoval < 0.5) {
                    adjustedValues[row][column] = 0
                }
            }
        }
        return adjustedValues
    }

    private fun List<MutableList<Int>>.copy(): List<MutableList<Int>> {
        val allZeroes = allZeroes()
        (0..8).forEach { row ->
            (0..8).forEach { column ->
                allZeroes[row][column] = this[row][column]
            }
        }
        return allZeroes
    }

    private fun allZeroes(): List<MutableList<Int>> {
        val allZeroes = (1..9).map {
            MutableList(9) { 0 }
        }
        return allZeroes
    }
}