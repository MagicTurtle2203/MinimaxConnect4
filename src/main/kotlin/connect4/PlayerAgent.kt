package connect4

import java.lang.NumberFormatException
import kotlin.io.readLine

class PlayerAgent(private val numCols: Int, private val numRows: Int,
                  private val lengthToWin: Int, private val popOut: Boolean) : AI {
    override fun getMove(boardState: List<List<Char>>): Move {
        val moveType: MoveType
        val columnNumber: Int

        inputmt@ while (true) {
            print("Enter move type (drop, pop, or exit): ")
            val inputMoveType = readLine()
            moveType = when (inputMoveType!!.toLowerCase()) {
                "drop" -> MoveType.DROP
                "pop" -> {
                    if (!popOut) {
                        println("Popping is not allowed in this game mode")
                        continue@inputmt
                    }
                    MoveType.POP
                }
                "exit" -> throw ExitGame("Player exited game")
                else -> {
                    println("Invalid move type entered")
                    continue@inputmt
                }
            }
            break
        }

        inputcn@ while (true) {
            print("Enter column number: ")
            val inputColumnNumber: Int
            try {
                inputColumnNumber = readLine()!!.toInt()
            } catch (e: NumberFormatException) {
                println("Invalid number")
                continue@inputcn
            }
            if (inputColumnNumber !in 0 until numCols) {
                println("Invalid column specified")
                continue@inputcn
            }
            columnNumber = inputColumnNumber
            break
        }

        return Move(moveType, columnNumber)
    }
}