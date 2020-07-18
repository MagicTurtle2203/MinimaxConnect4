package connect4

import kotlin.io.readLine

class PlayerAgent(numCols: Int, numRows: Int, lengthToWin: Int, popOut: Boolean)
    : AI(numCols, numRows, lengthToWin, popOut) {
    override fun getMove(): Move {
        TODO("Not yet implemented")
    }
}