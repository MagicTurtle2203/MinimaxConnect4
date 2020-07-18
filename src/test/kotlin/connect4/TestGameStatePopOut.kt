package connect4

import org.junit.jupiter.api.*

class TestGameStatePopOut {
    @Test
    fun testBoardIsCreatedWithCorrectDimensions() {
        val b = GameState(7, 6, popOut = true)
        Assertions.assertEquals(7, b.board.size)
        for (element in b.board) {
            Assertions.assertEquals(6, element.size)
        }
    }

    @Test
    fun testDropCorrectlyMovesPiecesToBottom() {
        val b = GameState(7, 6, popOut = true)
        b.drop(4)
        Assertions.assertEquals('X', b.board[4].last())
    }

    @Test
    fun testDropCorrectlySwitchesPlayerTurns() {
        val b = GameState(7, 6, popOut = true)
        for (i in 0 until 5) {
            b.drop(i)
            Assertions.assertEquals(when (i % 2) {
                0 -> 'X'
                else -> 'Y'
            }, b.board[i].last())
        }
    }

    @Test
    fun testDropHandlesMultipleDropsOnSameColumnCorrectly() {
        val b = GameState(1, 5)
        for (i in 0 until 5) {
            b.drop(0)
        }
        Assertions.assertEquals(listOf('X', 'Y', 'X', 'Y', 'X'), b.board[0])
    }

    @Test
    fun testDropThrowsExceptionForInvalidColumnNumber() {
        val b = GameState(1, 5)
        Assertions.assertThrows(InvalidMoveException::class.java) { b.drop(1) }
    }

    @Test
    fun testDropThrowsExceptionIfColumnFull() {
        val b = GameState(1, 5)
        for (i in 0 until 5) {
            b.drop(0)
        }
        Assertions.assertThrows(InvalidMoveException::class.java) { b.drop(0) }
    }

    @Test
    fun testPopRemovesBottomPieceCorrectly() {
        val b = GameState(1, 5, popOut = true)
        b.drop(0)
        b.drop(0)
        b.pop(0)
        Assertions.assertEquals('Y', b.board[0].last())
    }

    @Test
    fun testPopThrowsExceptionForInvalidColumnIndex() {
        val b = GameState(1, 5, popOut = true)
        b.drop(0)
        Assertions.assertThrows(InvalidMoveException::class.java) { b.pop(1) }
    }

    @Test
    fun testPopThrowsExceptionIfPoppingOpponentPiece() {
        val b = GameState(7, 6, popOut = true)
        b.drop(0)
        b.drop(1)
        Assertions.assertThrows(InvalidMoveException::class.java) { b.pop(1) }
    }

    @Test
    fun testPopThrowsExceptionOnEmptyColumn() {
        val b = GameState(7, 6, popOut = true)
        Assertions.assertThrows(InvalidMoveException::class.java) { b.pop(0) }
    }
}

class TestGameStatePopOutCheckWinner {
    @Test
    fun testFindsWinnerHorizontalX1() {
        val b = GameState(7, 6, popOut = true)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerHorizontalX2() {
        val b = GameState(7, 6, popOut = true)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerHorizontalX3() {
        val b = GameState(7, 6, popOut = true)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerHorizontalX4() {
        val b = GameState(7, 6, popOut = true)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerHorizontalY1() {
        val b = GameState(7, 6, popOut = true)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertEquals(Pair(true, Players.Y), b.checkWinner())
    }

    @Test
    fun testFindsWinnerHorizontalY2() {
        val b = GameState(7, 6, popOut = true)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertEquals(Pair(true, Players.Y), b.checkWinner())
    }

    @Test
    fun testFindsWinnerHorizontalY3() {
        val b = GameState(7, 6, popOut = true)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertEquals(Pair(true, Players.Y), b.checkWinner())
    }

    @Test
    fun testFindsWinnerHorizontalY4() {
        val b = GameState(7, 6, popOut = true)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertEquals(Pair(true, Players.Y), b.checkWinner())
    }

    @Test
    fun testFindsWinnerVerticalX1() {
        val b = GameState(7, 6, popOut = true)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerVerticalX2() {
        val b = GameState(7, 6, popOut = true)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerVerticalX3() {
        val b = GameState(7, 6, popOut = true)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerVerticalY1() {
        val b = GameState(7, 6, popOut = true)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertEquals(Pair(true, Players.Y), b.checkWinner())
    }
    @Test
    fun testFindsWinnerVerticalY2() {
        val b = GameState(7, 6, popOut = true)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertEquals(Pair(true, Players.Y), b.checkWinner())
    }

    @Test
    fun testFindsWinnerVerticalY3() {
        val b = GameState(7, 6, popOut = true)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertEquals(Pair(true, Players.Y), b.checkWinner())
    }

    @Test
    fun testFindsWinnerForwardDiagonalX1() {
        val b = GameState(7, 6, popOut = true)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerForwardDiagonalX2() {
        val b = GameState(7, 6, popOut = true)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerForwardDiagonalY1() {
        val b = GameState(7, 6, popOut = true)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertEquals(Pair(true, Players.Y), b.checkWinner())
    }

    @Test
    fun testFindsWinnerForwardDiagonalY2() {
        val b = GameState(7, 6, popOut = true)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertEquals(Pair(true, Players.Y), b.checkWinner())
    }

    @Test
    fun testFindsWinnerBackDiagonalX1() {
        val b = GameState(7, 6, popOut = true)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerBackDiagonalX2() {
        val b = GameState(7, 6, popOut = true)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerBackDiagonalY1() {
        val b = GameState(7, 6, popOut = true)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertEquals(Pair(true, Players.Y), b.checkWinner())
    }

    @Test
    fun testFindsWinnerBackDiagonalY2() {
        val b = GameState(7, 6, popOut = true)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertEquals(Pair(true, Players.Y), b.checkWinner())
    }

    @Test
    fun testFindsWinnerAfterPopHorizontalX() {
        val b = GameState(7, 6, popOut = true)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.pop(0)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerAfterPopHorizontalY() {
        val b = GameState(7, 6, popOut = true)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.pop(0)
        Assertions.assertEquals(Pair(true, Players.Y), b.checkWinner())
    }

    @Test
    fun testFindsWinnerForwardDiagonalX() {
        val b = GameState(7, 6, popOut = true)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.pop(2)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerBackDiagonalX() {
        val b = GameState(7, 6, popOut = true)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(5)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(6)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.pop(4)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }

    @Test
    fun testFindsWinnerWithCustomWinningLengthX() {
        val b = GameState(7, 6, 5, popOut = true)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(0)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(1)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(2)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(3)
        Assertions.assertFalse(b.checkWinner().first)
        b.drop(4)
        Assertions.assertEquals(Pair(true, Players.X), b.checkWinner())
    }
}