package connect4

class AIAgent(numCols: Int, numRows: Int, lengthToWin: Int, popOut: Boolean,
              private val player: Players, private val maxDepth: Int = 4)
    : AI(numCols, numRows, lengthToWin, popOut) {
    private val opponent = when (player) {
        Players.X -> Players.Y
        Players.Y -> Players.X
        else -> throw SomethingWentWrong("Player cannot be NONE")
    }

    override fun getMove(boardState: List<List<Char>>): Move {
        var bestMove = Move(MoveType.DROP, 0)
        var bestUtility = Int.MIN_VALUE

        for (column in 0 until numCols) {
            if (boardState[column].count { it == '.' } == 0) continue

            val rowIndex = boardState[column].lastIndexOf('.')
            val newColumn = boardState[column].toMutableList()
            newColumn[rowIndex] = player.token
            val nextBoardState = boardState.toMutableList()
            nextBoardState[column] = newColumn

            val utility = getMinMaxValue(nextBoardState, maxDepth - 1)
            if (utility > bestUtility) {
                bestUtility = utility
                bestMove = Move(MoveType.DROP, column)
            }
        }
        println(bestMove)
        return bestMove
    }

    private fun getMinMaxValue(boardState: List<List<Char>>, maxDepth: Int,
                               isMaximizing: Boolean = false): Int {
        if (checkWinner(boardState) || maxDepth == 0) {
            return evaluateBoard(boardState)
        }

        var bestValue = if (isMaximizing) Int.MIN_VALUE else Int.MAX_VALUE

        for (nextColumn in 0 until numCols) {
            if (boardState[nextColumn].count { it == '.' } == 0) continue

            val rowIndex = boardState[nextColumn].lastIndexOf('.')
            val newColumn = boardState[nextColumn].toMutableList()
            newColumn[rowIndex] = if (isMaximizing) player.token else opponent.token
            val nextBoardState = boardState.toMutableList()
            nextBoardState[nextColumn] = newColumn

            bestValue = if (isMaximizing) {
                maxOf(bestValue, getMinMaxValue(nextBoardState,
                        maxDepth - 1, false))
            } else {
                minOf(bestValue, getMinMaxValue(nextBoardState,
                        maxDepth - 1, true))
            }
        }

        return bestValue
    }

    private fun evaluateBoard(boardState: List<List<Char>>): Int {
        val scoreMap = mutableMapOf<Int, Int>()
        val newBoard = boardState.map { it.toMutableList() }

        for (length in lengthToWin downTo 2) {
            // Check horizontal
            for (row in 0 until numRows) {
                for (col in 0..numCols - length) {
                    val tokenList = (0 until length).map { add -> newBoard[col + add][row] }
                    if (tokenList.count { it == opponent.token } == lengthToWin)
                        return -100000
                    else if (tokenList.count { it == player.token } == length) {
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                        (0 until length).forEach { add -> newBoard[col + add][row] = '.' }
                    }
                }
            }

            // Check Vertical
            for (col in 0 until numCols) {
                for (row in 0..numRows - length) {
                    val tokenList = newBoard[col].slice(row until (row + length))
                    if (tokenList.count { it == opponent.token } == lengthToWin)
                        return -100000
                    else if (tokenList.count { it == player.token } == length) {
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                        (row until (row + length)).forEach { newBoard[col][it] = '.' }
                    }
                }
            }

            // Check forward slash diagonal
            for (col in 0..numCols - length) {
                for (row in 0..numRows - length) {
                    val tokenList = (0 until length).map { add -> newBoard[col + add][row + add] }
                    if (tokenList.count { it == opponent.token } == lengthToWin)
                        return -100000
                    else if (tokenList.count { it == player.token } == length) {
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                        (0 until length).forEach { add ->
                            newBoard[col + add][row + add] = '.'
                        }
                    }
                }
            }

            // Check backslash diagonal
            for (col in (numCols - length) until numCols) {
                for (row in 0..numRows - length) {
                    val tokenList = (0 until length).map { add -> newBoard[col - add][row + add] }
                    if (tokenList.count { it == opponent.token } == lengthToWin)
                        return -100000
                    else if (tokenList.count { it == player.token } == length) {
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                        (0 until length).forEach { add ->
                            newBoard[col - add][row + add] = '.'
                        }
                    }
                }
            }
        }

        return scoreMap.toList().fold(0) { acc, (key, value) ->
            when (key == lengthToWin) {
                true -> acc + (key * key * value) * 10000
                false -> acc + (key * key * value)
            }
        }
    }

    private fun checkWinner(boardState: List<List<Char>>): Boolean {
        // Check Horizontal
        for (row in 0 until numRows) {
            for (col in 0..numCols - lengthToWin) {
                val player = boardState[col][row]
                if (player == '.') continue
                else if ((0 until lengthToWin).map { add -> boardState[col + add][row] }.all { it == player })
                    return true
            }
        }

        // Check Vertical
        for (col in 0 until numCols) {
            for (row in 0..numRows - lengthToWin) {
                val player = boardState[col][row]
                if (player == '.') continue
                else if (boardState[col].slice(row until (row + lengthToWin)).all { it == player })
                    return true
            }
        }

        // Check forward slash diagonal
        for (col in 0..numCols - lengthToWin) {
            for (row in 0..numRows - lengthToWin) {
                val player = boardState[col][row]
                if (player == '.') continue
                else if ((0 until lengthToWin).map { add -> boardState[col + add][row + add] }.all { it == player })
                    return true
            }
        }

        // Check backslash diagonal
        for (col in (numCols - lengthToWin + (1 - numCols % 2)) until numCols) {
            for (row in 0..numRows - lengthToWin) {
                val player = boardState[col][row]
                if (player == '.') continue
                else if ((0 until lengthToWin).map { add -> boardState[col - add][row + add] }.all { it == player })
                    return true
            }
        }

        // Check tie
        if (!popOut) {
            if (boardState.all { col -> col.count { row -> row == '.' } == 0 })
                return true
        }

        // No winner found
        return false
    }

}