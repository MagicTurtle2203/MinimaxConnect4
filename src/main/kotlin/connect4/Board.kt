package connect4

import kotlin.math.max

class Board(val numCols: Int, val numRows: Int) {
    private enum class Players(val token: Char) { X('X'), Y('Y') }
    private var turn: Players = Players.X
    private var lastPlayed: Pair<Int, Int> = Pair(0, 0)
    private val _board: Array<CharArray> = Array(numCols) { CharArray(numRows) { '.' } }
    val board: List<List<Char>>
        get() = _board.map { it.toList() }

    fun drop(colIndex: Int) {
        if (colIndex < 0 || colIndex >= numCols) {
            throw InvalidMoveException("Specified column number is not valid")
        }

        val rowIndex = _board[colIndex].lastIndexOf('.')

        if (rowIndex == -1) {
            throw InvalidMoveException("Column is full")
        } else {
            _board[colIndex][rowIndex] = when (turn) {
                Players.X -> {
                    turn = Players.Y
                    Players.X.token
                }
                Players.Y -> {
                    turn = Players.X
                    Players.Y.token
                }
            }
        }

        lastPlayed = Pair(colIndex, rowIndex)
    }

    fun checkWinner(): Pair<Boolean, Char> {
        val player = _board[lastPlayed.first][lastPlayed.second]

        // Check horizontal
        val minCol = maxOf(lastPlayed.first - 3, 0)
        val maxCol = minOf(lastPlayed.first, numCols - 4)

        for (col in minCol..maxCol) {
            if ((col..(col + 3)).map { _col -> _board[_col][lastPlayed.second] }.all { it == player })
                return Pair(true, player)
        }

        // Check vertical
        val minRow = maxOf(lastPlayed.second - 3, 0)
        val maxRow = minOf(lastPlayed.second, numRows - 4)

        for (row in minRow..maxRow) {
            if (_board[lastPlayed.first].slice(row..(row + 3)).all { it == player })
                return Pair(true, player)
        }

        // Check forward slash diagonal
        (minRow..maxRow).map { row -> Pair(row - lastPlayed.second + lastPlayed.first, row)
        }.filter { point ->
            (point.first >= 0 && point.first < numCols - 3) &&
            (point.second >= 0 && point.second < numRows - 3)
        }.forEach { (_col, _row) ->
            if ((0..3).map { add -> _board[_col + add][_row + add] }.all { it == player })
                return Pair(true, player)
        }

        // Check backslash diagonal
        (minRow..maxRow).map { row -> Pair(-row + lastPlayed.second + lastPlayed.first, row)
        }.filter { point ->
            (point.first > 2 && point.first < numCols - 1) &&
            (point.second >= 0 && point.second < numRows - 3)
        }.forEach { (_col, _row) ->
            if ((0..3).map { add -> _board[_col - add][_row + add] }.all { it == player })
                return Pair(true, player)
        }

        // No 4-in-a-rows found
        return Pair(false, ' ')
    }
}