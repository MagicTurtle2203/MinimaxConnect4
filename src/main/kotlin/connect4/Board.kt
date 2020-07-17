package connect4

class Board(private val numRows: Int, private val numCols: Int) {
    private enum class Players(val token: Char) { X('X'), Y('Y') }
    private var turn: Players = Players.X
    val board: Array<CharArray> = Array(numCols) { CharArray(numRows) { ' ' } }

    fun drop(colNumber: Int) {
        if (colNumber < 0 || colNumber >= numCols) {
            throw InvalidMoveException("Specified column number is not valid")
        }

        val index = board[colNumber].lastIndexOf(' ')

        if (index == -1) {
            throw InvalidMoveException("Column is full")
        } else {
            board[colNumber][index] = when (turn) {
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
    }
}