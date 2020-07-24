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
        TODO("Not yet implemented")
    }

    private fun getMinMaxValue(boardState: List<List<Char>>, column: Int, maxDepth: Int,
                               isMaximizing: Boolean): Int {
        TODO("Not yet implemented")
    }

    private fun checkWinner(boardState: List<List<Char>>): Boolean {
        TODO("Not yet implemented")
    }

    private fun evaluateBoard(boardState: List<List<Char>>): Int {
        TODO("Not yet implemented")
    }
}