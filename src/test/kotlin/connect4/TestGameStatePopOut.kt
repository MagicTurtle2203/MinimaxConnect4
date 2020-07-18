package connect4

import org.junit.jupiter.api.*

class TestGameStatePopOut {
    @Test
    fun testBoardIsCreatedWithCorrectDimensions() {
        val b = GameStatePopOut(7, 6)
        Assertions.assertEquals(7, b.board.size)
        for (element in b.board) {
            Assertions.assertEquals(6, element.size)
        }
    }

    @Test
    fun testDropCorrectlyMovesPiecesToBottom() {
        val b = GameStatePopOut(7, 6)
        b.drop(4)
        Assertions.assertEquals('X', b.board[4].last())
    }

    @Test
    fun testDropCorrectlySwitchesPlayerTurns() {
        val b = GameStatePopOut(7, 6)
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
        val b = GameStatePopOut(1, 5)
        for (i in 0 until 5) {
            b.drop(0)
        }
        Assertions.assertEquals(listOf('X', 'Y', 'X', 'Y', 'X'), b.board[0])
    }

    @Test
    fun testDropThrowsExceptionForInvalidColumnNumber() {
        val b = GameStatePopOut(1, 5)
        Assertions.assertThrows(InvalidMoveException::class.java) { b.drop(1) }
    }

    @Test
    fun testDropThrowsExceptionIfColumnFull() {
        val b = GameStatePopOut(1, 5)
        for (i in 0 until 5) {
            b.drop(0)
        }
        Assertions.assertThrows(InvalidMoveException::class.java) { b.drop(0) }
    }

    @Test
    fun testPopRemovesBottomPieceCorrectly() {
        val b = GameStatePopOut(1, 5)
        b.drop(0)
        b.drop(0)
        b.pop(0)
        Assertions.assertEquals('Y', b.board[0].last())
    }

    @Test
    fun testPopThrowsExceptionForInvalidColumnIndex() {
        val b = GameStatePopOut(1, 5)
        b.drop(0)
        Assertions.assertThrows(InvalidMoveException::class.java) { b.pop(1) }
    }

    @Test
    fun testPopThrowsExceptionIfPoppingOpponentPiece() {
        val b = GameStatePopOut(7, 6)
        b.drop(0)
        b.drop(1)
        Assertions.assertThrows(InvalidMoveException::class.java) { b.pop(1) }
    }

    @Test
    fun testPopThrowsExceptionOnEmptyColumn() {
        val b = GameStatePopOut(7, 6)
        Assertions.assertThrows(InvalidMoveException::class.java) { b.pop(0) }
    }
}