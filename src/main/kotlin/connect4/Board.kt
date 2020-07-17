package connect4

class Board(private val numRows: Int, private val numCols: Int) {
    enum class Players { X, Y }
    val board: Array<CharArray> = Array(numCols) { CharArray(numRows) { ' ' } }
    var turn: Players = Players.X

    fun drop(colNumber: Int) {
        if (colNumber < 0 || colNumber >= numCols) {
            throw InvalidMoveException("Specified column number is not valid")
        }

        for ((index, value) in board[colNumber].withIndex()) {
            if (value != ' ') {
                board[colNumber][index - 1] = when (turn) {
                    Players.X -> {
                        turn = Players.Y
                        'X'
                    }
                    Players.Y -> {
                        turn = Players.X
                        'Y'
                    }
                }
            }
        }
    }
}