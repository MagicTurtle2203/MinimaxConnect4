package connect4

abstract class AI(private val numCols: Int, private val numRows: Int,
                  private val lengthToWin: Int, private val popOut: Boolean) {
    abstract fun getMove(boardState: List<List<Char>>): Move
}