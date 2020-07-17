package connect4

import org.junit.jupiter.api.*

class TestBoard {
    @Test
    fun testBoardIsCreatedWithCorrectDimensions() {
        val b = Board(10, 5)
        Assertions.assertEquals(10, b.board.size)
        for (element in b.board) {
            Assertions.assertEquals(5, element.size)
        }
    }

    @Test
    fun testDropCorrectlyMovesPiecesToBottom() {
        val b = Board(5, 5)
        b.drop(4)
        Assertions.assertEquals('X', b.board[4].last())
    }

    @Test
    fun testDropCorrectlySwitchesPlayerTurns() {
        val b = Board(5, 5)
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
        val b = Board(1, 5)
        for (i in 0 until 5) {
            b.drop(0)
        }
        Assertions.assertEquals(listOf('X', 'Y', 'X', 'Y', 'X'), b.board[0])
    }

    @Test
    fun testDropThrowsExceptionForInvalidColumnNumber() {
        val b = Board(1, 5)
        Assertions.assertThrows(InvalidMoveException::class.java) { b.drop(1) }
    }

    @Test
    fun testDropThrowsExceptionIfColumnFull() {
        val b = Board(1, 5)
        for (i in 0 until 5) {
            b.drop(0)
        }
        Assertions.assertThrows(InvalidMoveException::class.java) { b.drop(0) }
    }

    @Test
    fun testBoardFindsWinnerHorizontalX1() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'X'), b.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerHorizontalX2() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'X'), b.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerHorizontalX3() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'X'), b.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerHorizontalX4() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'X'), b.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerHorizontalY1() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'Y'), b.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerHorizontalY2() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'Y'), b.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerHorizontalY3() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'Y'), b.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerHorizontalY4() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'Y'), b.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerVerticalX1() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'X'), b.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerVerticalX2() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'X'), b.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerVerticalX3() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'X'), b.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerVerticalY1() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'Y'), b.checkWinner())
    }
    @Test
    fun testBoardFindsWinnerVerticalY2() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'Y'), b.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerVerticalY3() {
        val b = Board(7, 6)
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
        Assertions.assertEquals(Pair(true, 'Y'), b.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerLeftDiagonalX1() {
        val b = arrayOf(
                charArrayOf('.', '.', '.', 'Y', 'X', 'Y'),
                charArrayOf('.', '.', '.', 'X', 'Y', 'X'),
                charArrayOf('.', '.', '.', '.', 'X', 'Y'),
                charArrayOf('.', '.', '.', '.', '.', 'X'),
                charArrayOf('.', '.', '.', '.', '.', 'Y'),
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', '.')
        )
        val board = Board(7, 6, b)
        board.drop(0)
        Assertions.assertEquals(Pair(true, 'X'), board.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerLeftDiagonalX2() {
        val b = arrayOf(
                charArrayOf('.', '.', 'X', 'Y', 'X', 'Y'),
                charArrayOf('.', '.', '.', 'X', 'Y', 'X'),
                charArrayOf('.', '.', '.', '.', 'X', 'Y'),
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', 'Y'),
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', '.')
        )
        val board = Board(7, 6, b)
        board.drop(3)
        Assertions.assertEquals(Pair(true, 'X'), board.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerLeftDiagonalY1() {
        val b = arrayOf(
                charArrayOf('.', '.', '.', 'X', 'Y', 'X'),
                charArrayOf('.', '.', '.', 'Y', 'X', 'Y'),
                charArrayOf('.', '.', '.', '.', 'Y', 'X'),
                charArrayOf('.', '.', '.', '.', '.', 'Y'),
                charArrayOf('.', '.', '.', '.', '.', 'X'),
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', '.')
        )
        val board = Board(7, 6, b)
        board.drop(6)
        Assertions.assertFalse(board.checkWinner().first)
        board.drop(0)
        Assertions.assertEquals(Pair(true, 'Y'), board.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerLeftDiagonalY2() {
        val b = arrayOf(
                charArrayOf('.', '.', 'Y', 'X', 'Y', 'X'),
                charArrayOf('.', '.', '.', 'Y', 'X', 'Y'),
                charArrayOf('.', '.', '.', '.', 'Y', 'X'),
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', 'X'),
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', '.')
        )
        val board = Board(7, 6, b)
        board.drop(6)
        Assertions.assertFalse(board.checkWinner().first)
        board.drop(3)
        Assertions.assertEquals(Pair(true, 'Y'), board.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerRightDiagonalX1() {
        val b = arrayOf(
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', 'Y'),
                charArrayOf('.', '.', '.', '.', '.', 'X'),
                charArrayOf('.', '.', '.', '.', 'X', 'Y'),
                charArrayOf('.', '.', '.', 'X', 'Y', 'X'),
                charArrayOf('.', '.', '.', 'Y', 'X', 'Y')
        )
        val board = Board(7, 6, b)
        board.drop(6)
        Assertions.assertEquals(Pair(true, 'X'), board.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerRightDiagonalX2() {
        val b = arrayOf(
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', 'Y'),
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', 'X', 'Y'),
                charArrayOf('.', '.', '.', 'X', 'Y', 'X'),
                charArrayOf('.', '.', 'X', 'Y', 'X', 'Y')
        )
        val board = Board(7, 6, b)
        board.drop(3)
        Assertions.assertEquals(Pair(true, 'X'), board.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerRightDiagonalY1() {
        val b = arrayOf(
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', 'X'),
                charArrayOf('.', '.', '.', '.', '.', 'Y'),
                charArrayOf('.', '.', '.', '.', 'Y', 'X'),
                charArrayOf('.', '.', '.', 'Y', 'X', 'Y'),
                charArrayOf('.', '.', '.', 'X', 'Y', 'X')
        )
        val board = Board(7, 6, b)
        board.drop(0)
        Assertions.assertFalse(board.checkWinner().first)
        board.drop(6)
        Assertions.assertEquals(Pair(true, 'Y'), board.checkWinner())
    }

    @Test
    fun testBoardFindsWinnerRightDiagonalY2() {
        val b = arrayOf(
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', '.', 'X'),
                charArrayOf('.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '.', '.', '.', 'Y', 'X'),
                charArrayOf('.', '.', '.', 'Y', 'X', 'Y'),
                charArrayOf('.', '.', 'Y', 'X', 'Y', 'X')
        )
        val board = Board(7, 6, b)
        board.drop(0)
        Assertions.assertFalse(board.checkWinner().first)
        board.drop(3)
        Assertions.assertEquals(Pair(true, 'Y'), board.checkWinner())
    }
}