package connect4

class Board(private val numRows: Int, private val numCols: Int) {
    private enum class Players {
        X, Y
    }
    private val board: Array<CharArray> = Array(numCols) { CharArray(numRows) { ' ' } }
    private var turn: Players = Players.X
}