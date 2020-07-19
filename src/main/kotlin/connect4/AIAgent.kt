package connect4

class AIAgent(numCols: Int, numRows: Int, lengthToWin: Int, popOut: Boolean)
    : AI(numCols, numRows, lengthToWin, popOut) {
    override fun getMove(boardState: List<List<Char>>): Move {
        TODO("Not yet implemented")
    }
}