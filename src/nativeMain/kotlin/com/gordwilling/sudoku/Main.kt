package com.gordwilling.sudoku

fun main() {
    val easy = SudokuMaker.create(Difficulty.Easy)
    val medium = SudokuMaker.create(Difficulty.Medium)
    val hard = SudokuMaker.create(Difficulty.Hard)
    val expert = SudokuMaker.create(Difficulty.Expert)

    val easyPuzzle = fillGridTemplate(easy)
    val mediumPuzzle = fillGridTemplate(medium)
    val hardPuzzle = fillGridTemplate(hard)
    val expertPuzzle = fillGridTemplate(expert)

    println("Easy:")
    println(easyPuzzle)
    println()

    println("Medium:")
    println(mediumPuzzle)
    println()

    println("Hard:")
    println(hardPuzzle)
    println()

    println("Expert:")
    println(expertPuzzle)
    println()
}

private fun fillGridTemplate(gridValues: List<List<Int>>): String {
    val blankValue = " "
    val gridBuffer = StringBuilder(VisualGrid.gridTemplate)
    (0..8).forEach { row ->
        (0..8).forEach { column ->
            val placeholderIndex = gridBuffer.indexOf("$row$column")
            gridBuffer.deleteRange(placeholderIndex, placeholderIndex + 2)
            val gridValue = gridValues[row][column]
            val fillValue = if (gridValue != 0) gridValue.toString() else blankValue
            gridBuffer.insert(placeholderIndex, fillValue)
        }
    }
    return gridBuffer.toString()
}


