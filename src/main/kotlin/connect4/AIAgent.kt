package connect4

class AIAgent(numCols: Int, numRows: Int, lengthToWin: Int, popOut: Boolean, private val player: Players)
    : AI(numCols, numRows, lengthToWin, popOut) {

    override fun getMove(boardState: List<List<Char>>): Move {
        TODO("Not yet implemented")
    }

    private fun minimaxDecision(boardState: List<List<Char>>): Int {
        if (player == Players.X) {
            // max of minValue

        } else if (player == Players.Y) {
            // min of maxValue
        }
    }

    private fun minValue(boardState: List<List<Char>>, column: Int, maxDepth: Int): Int {
        if (boardState.all { col -> col.count { it == '.' } == 0 } || maxDepth == 0)
            return evaluateBoard(boardState)

        var bestValue = Int.MAX_VALUE

        boardState.withIndex().filter { (_, list) ->
            list.count { it == '.' } > 0
        }.forEach { (column, _) ->
            val newBoard = boardState.toMutableList()
            val rowIndex = newBoard[column].lastIndexOf('.')
            newBoard[column] = newBoard[column].mapIndexed { index, c ->
                when (index == rowIndex) {
                    true -> player.token
                    false -> c
                }
            }
            bestValue = minOf(bestValue, maxValue(newBoard, maxDepth - 1))
        }
        return bestValue
    }

    private fun maxValue(boardState: List<List<Char>>, maxDepth: Int): Int {
        if (boardState.all { col -> col.count { it == '.' } == 0 } || maxDepth == 0)
            return evaluateBoard(boardState)

        var bestValue = Int.MIN_VALUE

        boardState.withIndex().filter { (_, list) ->
            list.count { it == '.' } > 0
        }.forEach { (column, _) ->
            val newBoard = boardState.toMutableList()
            val rowIndex = newBoard[column].lastIndexOf('.')
            newBoard[column] = newBoard[column].mapIndexed { index, c ->
                when (index == rowIndex) {
                    true -> player.token
                    false -> c
                }
            }
            bestValue = maxOf(bestValue, minValue(newBoard, maxDepth - 1))
        }
        return bestValue
    }

    private fun evaluateBoard(boardState: List<List<Char>>): Int {
        val scoreMap = mutableMapOf<Int, Int>()

        // Check horizontal
        for (row in 0 until numRows) {
            for (col in 0..numCols - lengthToWin) {
                val tokenList = (0 until lengthToWin).map { add -> boardState[col + add][row] }
                when {
                    tokenList.all { it == 'Y' } -> return -10000
                    else -> {
                        val length = tokenList.count { it == 'X' }
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                    }
                }
            }
        }

        // Check Vertical
        for (col in 0 until numCols) {
            for (row in 0..numRows - lengthToWin) {
                val tokenList = (0 until lengthToWin).map { add -> boardState[col + add][row] }
                when {
                    tokenList.all { it == 'Y' } -> return -10000
                    else -> {
                        val length = tokenList.count { it == 'X' }
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                    }
                }
            }
        }

        // Check forward slash diagonal
        for (col in 0..numCols - lengthToWin) {
            for (row in 0..numRows - lengthToWin) {
                val tokenList = (0 until lengthToWin).map { add -> boardState[col + add][row] }
                when {
                    tokenList.all { it == 'Y' } -> return -10000
                    else -> {
                        val length = tokenList.count { it == 'X' }
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                    }
                }
            }
        }

        // Check backslash diagonal
        for (col in (numCols - lengthToWin + 1) until numCols) {
            for (row in 0..numRows - lengthToWin) {
                val tokenList = (0 until lengthToWin).map { add -> boardState[col + add][row] }
                when {
                    tokenList.all { it == 'Y' } -> return -10000
                    else -> {
                        val length = tokenList.count { it == 'X' }
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
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
}