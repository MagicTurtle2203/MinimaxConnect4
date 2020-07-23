package connect4

fun todo(): Nothing = TODO("Fix checkWinner function for diagonal case")

class AIAgent(numCols: Int, numRows: Int, lengthToWin: Int, popOut: Boolean, private val player: Players,
              private val maxDepth: Int)
    : AI(numCols, numRows, lengthToWin, popOut) {
    private val opponent = when (player) {
        Players.X -> Players.Y
        Players.Y -> Players.X
        else -> throw SomethingWentWrong("Agent should not be NONE")
    }
    private val moveTypes = mutableListOf(MoveType.DROP)
    init {
        if (popOut) moveTypes.add(MoveType.POP)
    }

    override fun getMove(boardState: List<List<Char>>): Move {
        when (player) {
            Players.X -> {
                // max of minValue
                var bestMove = Move(MoveType.DROP, 0)
                var bestValue = Int.MIN_VALUE

                for (move in moveTypes) {
                    colLoop@for (column in 0 until numCols) {
                         when (move) {
                            MoveType.DROP -> {
                                if (boardState[column].count { it == '.' } == 0)
                                    continue@colLoop
                            }
                            MoveType.POP -> {
                                if (boardState[column].last() != player.token)
                                    continue@colLoop
                            }
                        }
                        val received = minValue(boardState, column, move, maxDepth)
                        if (received > bestValue) {
                            bestMove = Move(move, column)
                            bestValue = received
                        }
                    }
                }

                println("$bestMove")
                return bestMove
            }
            Players.Y -> {
                // min of maxValue
                var bestMove = Move(MoveType.DROP, 0)
                var bestValue = Int.MAX_VALUE

                for (move in moveTypes) {
                    colLoop@for (column in 0 until numCols) {
                         when (move) {
                            MoveType.DROP -> {
                                if (boardState[column].count { it == '.' } == 0)
                                    continue@colLoop
                            }
                            MoveType.POP -> {
                                if (boardState[column].last() != player.token)
                                    continue@colLoop
                            }
                        }
                        val received = maxValue(boardState, column, move, maxDepth)
                        if (received < bestValue) {
                            bestMove = Move(move, column)
                            bestValue = received
                        }
                    }
                }

                println("$bestMove")
                return bestMove
            }
            else -> throw SomethingWentWrong("Agent shouldn't be NONE")
        }
    }

    private fun minValue(boardState: List<List<Char>>, column: Int, moveType: MoveType,
                         maxDepth: Int, isAgent: Boolean = true): Int {
        if (boardState.all { col -> col.count { it == '.' } == 0 } || maxDepth == 0)
            return evaluateBoard(boardState)

        var bestValue = Int.MAX_VALUE
        val newBoard = boardState.toMutableList()
        val newColumn = when (moveType) {
            MoveType.DROP -> {
                val nc = newBoard[column].toMutableList()
                nc[nc.lastIndexOf('.')] = if (isAgent) player.token else opponent.token
                nc
            }
            MoveType.POP -> {
                 List(numRows) { '.' }.mapIndexed { index, c ->
                    when (index > 0) {
                        true -> newBoard[column][index - 1]
                        false -> c
                    }
                }
            }
        }
        newBoard[column] = newColumn

        for (move in moveTypes) {
            colLoop@for (nextColumn in 0 until numCols) {
                when (move) {
                    MoveType.DROP -> {
                        if (newBoard[nextColumn].count { it == '.' } == 0)
                            continue@colLoop
                    }
                    MoveType.POP -> {
                        if (newBoard[nextColumn].last() != player.token)
                            continue@colLoop
                    }
                }
                bestValue = minOf(bestValue,
                        maxValue(newBoard, nextColumn, move, maxDepth - 1, !isAgent))
            }
        }
        return bestValue
    }

    private fun maxValue(boardState: List<List<Char>>, column: Int, moveType: MoveType,
                         maxDepth: Int, isAgent: Boolean = true): Int {
        if (boardState.all { col -> col.count { it == '.' } == 0 } || maxDepth == 0)
            return evaluateBoard(boardState)

        var bestValue = Int.MIN_VALUE
        val newBoard = boardState.toMutableList()
        val newColumn = when (moveType) {
            MoveType.DROP -> {
                val nc = newBoard[column].toMutableList()
                nc[nc.lastIndexOf('.')] = if (isAgent) player.token else opponent.token
                nc
            }
            MoveType.POP -> {
                List(numRows) { '.' }.mapIndexed { index, c ->
                    when (index > 0) {
                        true -> newBoard[column][index - 1]
                        false -> c
                    }
                }
            }
        }
        newBoard[column] = newColumn

        for (move in moveTypes) {
            colLoop@for (nextColumn in 0 until numCols) {
                when (move) {
                    MoveType.DROP -> {
                        if (newBoard[nextColumn].count { it == '.' } == 0) {
                            continue@colLoop
                        }
                    }
                    MoveType.POP -> {
                        if (newBoard[nextColumn].last() != player.token)
                            continue@colLoop
                    }
                }
                bestValue = maxOf(bestValue,
                        minValue(newBoard, nextColumn, move,maxDepth - 1, !isAgent))
            }
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
        for (col in (numCols - lengthToWin) until numCols) {
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