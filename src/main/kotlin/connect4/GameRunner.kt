package connect4

enum class Modes { DEFAULT, PVP, AIONLY }

class GameRunner(mode: Modes, boardCols: Int, boardRows: Int) {
    private val board = GameStatePopOut(boardCols, boardRows)
    private val player1: AI
    private val player2: AI

    init {
        when (mode) {
            Modes.PVP -> {
                player1 = PlayerAI()
                player2 = PlayerAI()
            }
            Modes.AIONLY -> {
                player1 = AIAgent()
                player2 = AIAgent()
            }
            Modes.DEFAULT -> {
                player1 = PlayerAI()
                player2 = AIAgent()
            }
        }
    }

    fun run(): Players {
        var turn = 1

        while (true) {
            when (turn % 2) {
                1 -> board.drop(player1.getMove())
                0 -> board.drop(player2.getMove())
            }
            val (hasWinner, winner) = board.checkWinner()
            if (hasWinner) return winner
            turn = turn % 2 + 1
        }
    }
}