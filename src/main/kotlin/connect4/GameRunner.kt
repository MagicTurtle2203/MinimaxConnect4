package connect4

enum class Modes { DEFAULT, PVP, AIONLY }

class GameRunner(mode: Modes, boardCols: Int, boardRows: Int, lengthToWin: Int = 4, popOut: Boolean = false) {
    private val board = GameState(boardCols, boardRows, lengthToWin, popOut)
    private val player1: AI
    private val player2: AI

    init {
        when (mode) {
            Modes.DEFAULT -> {
                player1 = PlayerAgent(boardCols, boardRows, lengthToWin, popOut)
                player2 = AIAgent(boardCols, boardRows, lengthToWin, popOut, Players.Y)
            }
            Modes.PVP -> {
                player1 = PlayerAgent(boardCols, boardRows, lengthToWin, popOut)
                player2 = PlayerAgent(boardCols, boardRows, lengthToWin, popOut)
            }
            Modes.AIONLY -> {
                player1 = AIAgent(boardCols, boardRows, lengthToWin, popOut, Players.X, 2)
                player2 = AIAgent(boardCols, boardRows, lengthToWin, popOut, Players.Y, 4)
            }
        }
    }

    fun run() {
        board.displayBoard()

        var turn = 1
        while (true) {
            try {
                val move = when (turn % 2) {
                    1 -> {
                        println("Player 1's Turn")
                        player1.getMove(board.board)
                    }
                    0 -> {
                        println("Player 2's Turn")
                        player2.getMove(board.board)
                    }
                    else -> throw SomethingWentWrong("Modulo 2 should only give 1 or 0")
                }
                when (move.moveType) {
                    MoveType.DROP -> board.drop(move.columnIndex)
                    MoveType.POP -> board.pop(move.columnIndex)
                }
            } catch (e: InvalidMoveException) {
                println("Invalid move: ${e.message}")
            } catch (e: ExitGame) {
                println("Exiting...")
                break
            }
            board.displayBoard()

            val (hasWinner, winner) = board.checkWinner()
            if (hasWinner) {
                when (winner) {
                    Players.X -> println("Player 1 Wins")
                    Players.Y -> println("Player 2 Wins")
                    Players.NONE -> println("Tie")
                }
            }
            turn = turn % 2 + 1
        }
    }
}