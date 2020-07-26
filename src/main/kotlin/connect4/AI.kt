package connect4

interface AI {
    fun getMove(boardState: List<List<Char>>): Move
}