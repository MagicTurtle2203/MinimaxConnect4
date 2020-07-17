package connect4

class Board(private val numRows: Int, private val numCols: Int) {
    private enum class Players { X, Y }
    private var turn: Players = Players.X
    val board: Array<CharArray> = Array(numCols) { CharArray(numRows) { ' ' } }

    fun drop(colNumber: Int) {
        if (colNumber < 0 || colNumber >= numCols) {
            throw InvalidMoveException("Specified column number is not valid")
        }

        val index = board[colNumber].lastIndexOf(' ')
        board[colNumber][index] = when (turn) {
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