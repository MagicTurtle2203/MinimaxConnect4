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
}