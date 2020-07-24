package connect4

class AIAgent(numCols: Int, numRows: Int, lengthToWin: Int, popOut: Boolean,
              private val player: Players, private val maxDepth: Int = 4)
    : AI(numCols, numRows, lengthToWin, popOut) {
    private val opponent = when (player) {
        Players.X -> Players.Y
        Players.Y -> Players.X
        else -> throw SomethingWentWrong("Player cannot be NONE")
    }

    override fun getMove(boardState: List<List<Char>>): Move {
        var bestMove = Move(MoveType.DROP, 0)
        var bestUtility = Double.NEGATIVE_INFINITY

        for (column in 0 until numCols) {
            if (boardState[column].count { it == '.' } == 0) continue
            val utility = getMinMaxValue(boardState, column, maxDepth)
            if (utility > bestUtility) {
                bestUtility = utility
                bestMove = Move(MoveType.DROP, column)
            }
        }

        return bestMove
    }

    private fun getMinMaxValue(boardState: List<List<Char>>, column: Int, maxDepth: Int,
                               isMaximizing: Boolean = true): Double {
        if (checkWinner(boardState) || maxDepth == 0)
            return evaluateBoard(boardState)

        val rowIndex = boardState[column].lastIndexOf('.')
        val newColumn = boardState[column].toMutableList()
        newColumn[rowIndex] = if (isMaximizing) player.token else opponent.token
        val nextBoardState = boardState.toMutableList()
        nextBoardState[column] = newColumn

        var bestValue = if (isMaximizing) Double.NEGATIVE_INFINITY else Double.POSITIVE_INFINITY

        for (nextColumn in 0 until numCols) {
            if (nextBoardState[nextColumn].count { it == '.' } == 0) continue
            bestValue = maxOf(bestValue, getMinMaxValue(nextBoardState, nextColumn,
                    maxDepth - 1, !isMaximizing))
        }

        return bestValue
    }

    private fun checkWinner(boardState: List<List<Char>>): Boolean {
        TODO("Not yet implemented")
    }

    private fun evaluateBoard(boardState: List<List<Char>>): Double {
        TODO("Not yet implemented")
    }
}