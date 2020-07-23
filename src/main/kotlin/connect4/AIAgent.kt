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
        val winnerStatus = checkWinner(boardState)
        if (winnerStatus.first && winnerStatus.second == 'Y')
            return -10000
        else if (winnerStatus.first || maxDepth == 0)
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
        val winnerStatus = checkWinner(boardState)
        if (winnerStatus.first && winnerStatus.second == 'Y')
            return -10000
        else if (winnerStatus.first || maxDepth == 0)
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
        val newBoard = boardState.map { it.toMutableList() }

//        for (i in 0 until numRows) {
//            print("$i ")
//            for (j in 0 until numCols) {
//                print("${newBoard[j][i]} ")
//            }
//            println("")
//        }
//        println("  ${(0 until numCols).joinToString(separator = " ")}")

        for (length in lengthToWin downTo 2) {
            // Check horizontal
            for (row in 0 until numRows) {
                for (col in 0..numCols - length) {
                    val tokenList = (0 until length).map { add -> newBoard[col + add][row] }
                    if (tokenList.count { it == 'X' } == length) {
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                        (0 until length).forEach { add -> newBoard[col + add][row] = '.' }
                    }
                }
            }

            // Check Vertical
            for (col in 0 until numCols) {
                for (row in 0..numRows - length) {
                    val tokenList = newBoard[col].slice(row until (row + length))
                    if (tokenList.count { it == 'X' } == length) {
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                        (row until (row + length)).forEach { newBoard[col][it] = '.' }
                    }
                }
            }

            // Check forward slash diagonal
            for (col in 0..numCols - length) {
                for (row in 0..numRows - length) {
                    val tokenList = (0 until length).map { add -> newBoard[col + add][row + add] }
                    if (tokenList.count { it == 'X' } == length) {
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
                    if (tokenList.count { it == 'X' } == length) {
                        scoreMap[length] = scoreMap.getOrDefault(length, 0) + 1
                        (0 until length).forEach { add ->
                            newBoard[col - add][row + add] = '.'
                        }
                    }
                }
            }
        }

//        for (i in 0 until numRows) {
//            print("$i ")
//            for (j in 0 until numCols) {
//                print("${newBoard[j][i]} ")
//            }
//            println("")
//        }
//        println("  ${(0 until numCols).joinToString(separator = " ")}")
//
//        println(scoreMap.toList().fold(0) { acc, (key, value) ->
//            when (key == lengthToWin) {
//                true -> acc + (key * key * value) * 10000
//                false -> acc + (key * key * value)
//            }
//        })
//
//        println("------------------------------------------------------")

        return scoreMap.toList().fold(0) { acc, (key, value) ->
            when (key == lengthToWin) {
                true -> acc + (key * key * value) * 10000
                false -> acc + (key * key * value)
            }
        }
    }

    private fun checkWinner(boardState: List<List<Char>>): Pair<Boolean, Char> {
        // Check Horizontal
        for (row in 0 until numRows) {
            for (col in 0..numCols - lengthToWin) {
                val player = boardState[col][row]
                if (player == '.') continue
                else if ((0 until lengthToWin).map { add -> boardState[col + add][row] }.all { it == player })
                    return Pair(true, player)
            }
        }

        // Check Vertical
        for (col in 0 until numCols) {
            for (row in 0..numRows - lengthToWin) {
                val player = boardState[col][row]
                if (player == '.') continue
                else if (boardState[col].slice(row until (row + lengthToWin)).all { it == player })
                    return Pair(true, player)
            }
        }

        // Check forward slash diagonal
        for (col in 0..numCols - lengthToWin) {
            for (row in 0..numRows - lengthToWin) {
                val player = boardState[col][row]
                if (player == '.') continue
                else if ((0 until lengthToWin).map { add -> boardState[col + add][row + add] }.all { it == player })
                    return Pair(true, player)
            }
        }

        // Check backslash diagonal
        for (col in (numCols - lengthToWin + (1 - numCols % 2)) until numCols) {
            for (row in 0..numRows - lengthToWin) {
                val player = boardState[col][row]
                if (player == '.') continue
                else if ((0 until lengthToWin).map { add -> boardState[col - add][row + add] }.all { it == player })
                    return Pair(true, player)
            }
        }

        // Check tie
        if (!popOut) {
            if (boardState.all { col -> col.count { row -> row == '.' } == 0 })
                return Pair(true, '.')
        }

        // No winner found
        return Pair(false, '.')
    }
}