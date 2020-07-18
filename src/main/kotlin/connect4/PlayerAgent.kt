package connect4

import java.lang.NumberFormatException
import kotlin.io.readLine

class PlayerAgent(numCols: Int, numRows: Int, lengthToWin: Int, popOut: Boolean)
    : AI(numCols, numRows, lengthToWin, popOut) {
    override fun getMove(): Move {
        val moveType: MoveType
        val columnNumber: Int

        inputmt@ while (true) {
            print("Enter move type (drop or pop): ")
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