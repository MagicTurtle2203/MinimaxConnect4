package connect4

class GameStatePopOut(val numCols: Int, val numRows: Int) {
    private val _board: Array<CharArray> = Array(numCols) { CharArray(numRows) { '.' } }
    val board: List<List<Char>> get() = _board.map { it.toList() }
    private var turn: Players = Players.X

    fun drop(colIndex: Int) {
        if (colIndex !in 0 until numCols) {
            throw InvalidMoveException("Column index out of range")
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
                else -> {
                    throw SomethingWentWrong("I'm not sure how this happened")
                }
            }
        }
    }

    fun pop(colIndex: Int) {
        if (colIndex !in 0 until numCols || _board[colIndex].last() != turn.token) {
            throw InvalidMoveException("Can only pop your own pieces from the bottom")
        }

        val newCol = CharArray(numRows) { '.' }.mapIndexed { index, c ->
            when (index > 0) {
                true -> _board[colIndex][index - 1]
                false -> c
            }
        }.toCharArray()

        _board[colIndex] = newCol
    }
}