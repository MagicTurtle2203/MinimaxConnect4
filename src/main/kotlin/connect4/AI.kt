package connect4

abstract class AI(val numCols: Int, val numRows: Int, val lengthToWin: Int, val popOut: Boolean) {
    abstract fun getMove(boardState: List<List<Char>>): Move
}