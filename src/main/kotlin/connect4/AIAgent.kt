package connect4

fun todo(): Nothing = TODO("DONE: Make evaluation function specific to agent (calculate for agent's tokens)," +
        "DONE: Agent is always maximizing, so no need for check in getMove" +
        "DONE: Combine minValue and maxValue into one function" +
        "Fix checkWinner function for diagonal case")

class AIAgent(numCols: Int, numRows: Int, lengthToWin: Int, popOut: Boolean, private val player: Players)
    : AI(numCols, numRows, lengthToWin, popOut) {
    private val opponent = when (player) {
        Players.X -> Players.Y
        Players.Y -> Players.X
        else -> throw SomethingWentWrong("Agent should not be NONE")
    }

    override fun getMove(boardState: List<List<Char>>): Move {
        // max of minValue
        var bestColumn = 0
        var bestValue = Int.MIN_VALUE

        for (column in 0 until numCols) {
            val received = getMinMaxValue(boardState, column, 4)
            println("received: $received, bestValue: $bestValue, bestColumn: $bestColumn")
            if (received > bestValue) {
                bestColumn = column
                bestValue = received
            }
        }
        println("DROP $bestColumn")
        return Move(MoveType.DROP, bestColumn)
    }

    private fun getMinMaxValue(boardState: List<List<Char>>, column: Int, maxDepth: Int,
                               isMaximizing: Boolean = true): Int {
        if (boardState.all { col -> col.count { it == '.' } == 0 } || maxDepth == 0)
            return evaluateBoard(boardState)
        if (boardState[column].count { it == '.' } == 0)
            return if (isMaximizing) Int.MAX_VALUE else Int.MIN_VALUE

        var bestValue = if (isMaximizing) Int.MIN_VALUE else Int.MAX_VALUE

        val newBoard = boardState.toMutableList()
        val rowIndex = newBoard[column].lastIndexOf('.')
        newBoard[column] = newBoard[column].mapIndexed { index, c ->
            when (index == rowIndex) {
                true -> if (isMaximizing) player.token else opponent.token
                false -> c
            }
        }

        for (nextColumn in 0 until numCols)
            bestValue = if (isMaximizing)
                maxOf(bestValue, getMinMaxValue(newBoard, nextColumn, maxDepth - 1, !isMaximizing))
            else
                minOf(bestValue, getMinMaxValue(newBoard, nextColumn, maxDepth - 1, !isMaximizing))

        return bestValue
    }

    private fun evaluateBoard(boardState: List<List<Char>>): Int {
        val scoreMap = mutableMapOf<Int, Int>()

        // Check horizontal
        for (row in 0 until numRows) {
            for (col in 0..numCols - lengthToWin) {
                if (boardState[col].count { it == '.' } == 0)
                    scoreMap[-1] = scoreMap.getOrDefault(-1, 0) + 2
                val tokenList = (0 until lengthToWin).map { add -> boardState[col + add][row] }
                when {
                    tokenList.all { it == opponent.token } -> return -10000
                    tokenList.count { it == player.token } == lengthToWin - 1 &&
                            tokenList.count { it == '.'} == 1 ->
                        scoreMap[lengthToWin - 1] = scoreMap.getOrDefault(lengthToWin - 1, 0) + 100
                    else -> {
                        val length = tokenList.count { it == player.token }
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                    }
                }
            }
        }

        // Check Vertical
        for (col in 0 until numCols) {
            if (boardState[col].count { it == '.' } == 0)
                scoreMap[-1] = scoreMap.getOrDefault(-1, 0) + 2
            for (row in 0..numRows - lengthToWin) {
                val tokenList = boardState[col].slice(row until (row + lengthToWin))
                when {
                    tokenList.all { it == opponent.token } -> return -10000
                    tokenList.count { it == player.token } == lengthToWin - 1 &&
                            tokenList.count { it == '.'} == 1 ->
                        scoreMap[lengthToWin - 1] = scoreMap.getOrDefault(lengthToWin - 1, 0) + 100
                    else -> {
                        val length = tokenList.count { it == player.token }
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                    }
                }
            }
        }

        // Check forward slash diagonal
        for (col in 0..numCols - lengthToWin) {
            if (boardState[col].count { it == '.' } == 0)
                scoreMap[-1] = scoreMap.getOrDefault(-1, 0) + 2
            for (row in 0..numRows - lengthToWin) {
                val tokenList = (0 until lengthToWin).map { add -> boardState[col + add][row + add] }
                when {
                    tokenList.all { it == opponent.token } -> return -10000
                    tokenList.count { it == player.token } == lengthToWin - 1 &&
                            tokenList.count { it == '.'} == 1 ->
                        scoreMap[lengthToWin - 1] = scoreMap.getOrDefault(lengthToWin - 1, 0) + 100
                    else -> {
                        val length = tokenList.count { it == player.token }
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                    }
                }
            }
        }

        // Check backslash diagonal
        for (col in (numCols - lengthToWin + 1) until numCols) {
            if (boardState[col].count { it == '.' } == 0)
                scoreMap[-1] = scoreMap.getOrDefault(-1, 0) + 2
            for (row in 0..numRows - lengthToWin) {
                val tokenList = (0 until lengthToWin).map { add -> boardState[col - add][row + add] }
                when {
                    tokenList.all { it == opponent.token } -> return -10000
                    tokenList.count { it == player.token } == lengthToWin - 1 &&
                            tokenList.count { it == '.'} == 1 ->
                        scoreMap[lengthToWin - 1] = scoreMap.getOrDefault(lengthToWin - 1, 0) + 100
                    else -> {
                        val length = tokenList.count { it == player.token }
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                    }
                }
            }
        }

        return scoreMap.toList().fold(0) { acc, (key, value) ->
            when (key == lengthToWin) {
                true -> acc + (key * key * key * value) * 10000
                false -> acc + (key * key * key * value)
            }
        }
    }
}