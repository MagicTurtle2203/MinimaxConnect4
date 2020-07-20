package connect4

fun todo(): Nothing = TODO("Fix checkWinner function for diagonal case")

class AIAgent(numCols: Int, numRows: Int, lengthToWin: Int, popOut: Boolean, private val player: Players)
    : AI(numCols, numRows, lengthToWin, popOut) {
    private val opponent = when (player) {
        Players.X -> Players.Y
        Players.Y -> Players.X
        else -> throw SomethingWentWrong("Agent should not be NONE")
    }

    override fun getMove(boardState: List<List<Char>>): Move {
        when (player) {
            Players.X -> {
                // max of minValue
                var bestColumn = 0
                var bestValue = Int.MIN_VALUE

                for (column in 0 until numCols) {
                    val received = minValue(boardState, column, 4)
//                    println("received: $received, bestValue: $bestValue, bestColumn: $bestColumn")
                    if (received > bestValue) {
                        bestColumn = column
                        bestValue = received
                    }
                }
                println("DROP $bestColumn")
                return Move(MoveType.DROP, bestColumn)
            }
            Players.Y -> {
                // min of maxValue
                var bestColumn = 0
                var bestValue = Int.MAX_VALUE

                for (column in 0 until numCols) {
                    val received = maxValue(boardState, column, 4)
//                    println("received: $received, bestValue: $bestValue, bestColumn: $bestColumn")
                    if (received < bestValue) {
                        bestColumn = column
                        bestValue = received
                    }
                }
                println("DROP $bestColumn")
                return Move(MoveType.DROP, bestColumn)
            }
            else -> throw SomethingWentWrong("Agent shouldn't be NONE")
        }
    }

    private fun minValue(boardState: List<List<Char>>, column: Int,
                         maxDepth: Int, isAgent: Boolean = true): Int {
        if (boardState.all { col -> col.count { it == '.' } == 0 } || maxDepth == 0)
            return evaluateBoard(boardState)
        if (boardState[column].count { it == '.' } == 0)
            return Int.MIN_VALUE

        var bestValue = Int.MAX_VALUE

        val newBoard = boardState.toMutableList()
        val rowIndex = newBoard[column].lastIndexOf('.')
        newBoard[column] = newBoard[column].mapIndexed { index, c ->
            when (index == rowIndex) {
                true -> if (isAgent) player.token else opponent.token
                false -> c
            }
        }

        for (nextColumn in 0 until numCols)
            bestValue = minOf(bestValue, maxValue(newBoard, nextColumn, maxDepth - 1, !isAgent))

        return bestValue
    }

    private fun maxValue(boardState: List<List<Char>>, column: Int,
                         maxDepth: Int, isAgent: Boolean = true): Int {
        if (boardState.all { col -> col.count { it == '.' } == 0 } || maxDepth == 0)
            return evaluateBoard(boardState)
        if (boardState[column].count { it == '.' } == 0)
            return Int.MAX_VALUE

        var bestValue = Int.MIN_VALUE

        val newBoard = boardState.toMutableList()
        val rowIndex = newBoard[column].lastIndexOf('.')
        newBoard[column] = newBoard[column].mapIndexed { index, c ->
            when (index == rowIndex) {
                true -> if (isAgent) player.token else opponent.token
                false -> c
            }
        }

        for (nextColumn in 0 until numCols)
            bestValue = maxOf(bestValue, minValue(newBoard, nextColumn, maxDepth - 1, !isAgent))

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
                    tokenList.count { it == 'X' } == lengthToWin - 1 && tokenList.count { it == '.'} == 1 ->
                        scoreMap[lengthToWin - 1] = scoreMap.getOrDefault(lengthToWin - 1, 0) + 100
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
                val tokenList = boardState[col].slice(row until (row + lengthToWin))
                when {
                    tokenList.all { it == 'Y' } -> return -10000
                    tokenList.count { it == 'X' } == lengthToWin - 1 && tokenList.count { it == '.'} == 1 ->
                        scoreMap[lengthToWin - 1] = scoreMap.getOrDefault(lengthToWin - 1, 0) + 100
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
                val tokenList = (0 until lengthToWin).map { add -> boardState[col + add][row + add] }
                when {
                    tokenList.all { it == 'Y' } -> return -10000
                    tokenList.count { it == 'X' } == lengthToWin - 1 && tokenList.count { it == '.'} == 1 ->
                        scoreMap[lengthToWin - 1] = scoreMap.getOrDefault(lengthToWin - 1, 0) + 100
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
                val tokenList = (0 until lengthToWin).map { add -> boardState[col - add][row + add] }
                when {
                    tokenList.all { it == 'Y' } -> return -10000
                    tokenList.count { it == 'X' } == lengthToWin - 1 && tokenList.count { it == '.'} == 1 ->
                        scoreMap[lengthToWin - 1] = scoreMap.getOrDefault(lengthToWin - 1, 0) + 100
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