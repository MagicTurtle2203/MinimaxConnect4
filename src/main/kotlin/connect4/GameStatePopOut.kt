package connect4

class GameStatePopOut(val numCols: Int, val numRows: Int) {
    private val _board: Array<Array<Players>> = Array(numCols) { Array(numRows) { Players.NONE } }
    val board: List<List<Char>> get() = _board.map { col -> col.map { item -> item.token }.toList() }
    private var turn: Players = Players.X

    fun drop(colIndex: Int) {
        if (colIndex !in 0 until numCols) {
            throw InvalidMoveException("Column index out of range")
        }

        val rowIndex = _board[colIndex].lastIndexOf(Players.NONE)

        if (rowIndex == -1) {
            throw InvalidMoveException("Column is full")
        } else {
            _board[colIndex][rowIndex] = when (turn) {
                Players.X -> {
                    turn = Players.Y
                    Players.X
                }
                Players.Y -> {
                    turn = Players.X
                    Players.Y
                }
                else -> throw SomethingWentWrong("This shouldn't happen")
            }
        }
    }

    fun pop(colIndex: Int) {
        if (colIndex !in 0 until numCols || _board[colIndex].last() != turn) {
            throw InvalidMoveException("Can only pop your own pieces from the bottom")
        }

        val newCol = Array(numRows) { Players.NONE }.mapIndexed { index, c ->
            when (index > 0) {
                true -> _board[colIndex][index - 1]
                false -> c
            }
        }.toTypedArray()

        _board[colIndex] = newCol

        turn = when (turn) {
            Players.X -> Players.Y
            Players.Y -> Players.X
            else -> throw SomethingWentWrong("This shouldn't happen")
        }
    }

    fun checkWinner(): Pair<Boolean, Players> {
        // Check Horizontal
        for (row in 0 until numRows) {
            for (col in 0..numCols - 4) {
                val player = _board[col][row]
                if (player == Players.NONE) continue
                else if ((0..3).map { add -> _board[col + add][row] }.all { it == player })
                    return Pair(true, player)
            }
        }

        // Check Vertical
        for (col in 0 until numCols) {
            for (row in 0..numRows - 4) {
                val player = _board[col][row]
                if (player == Players.NONE) continue
                else if (_board[col].slice(row..(row + 3)).all { it == player })
                    return Pair(true, player)
            }
        }

        // Check forward slash diagonal
        for (col in 0..numCols - 4) {
            for (row in 0..numRows - 4) {
                val player = _board[col][row]
                if (player == Players.NONE) continue
                else if ((0..3).map { add -> _board[col + add][row + add] }.all { it == player })
                    return Pair(true, player)
            }
        }

        // Check backslash diagonal
        for (col in (numCols - 4) until numCols) {
            for (row in 0..numRows - 4) {
                val player = _board[col][row]
                if (player == Players.NONE) continue
                else if ((0..3).map { add -> _board[col - add][row + add] }.all { it == player })
                    return Pair(true, player)
            }
        }

        // No winner found
        return Pair(false, Players.NONE)
    }
}