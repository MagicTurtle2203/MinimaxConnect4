package connect4

import org.junit.jupiter.api.*

class TestBoard {
    @Test
    fun testBoardIsCreatedWithCorrectDimensions() {
        val b = Board(5, 10)
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
        val b = Board(5, 1)
        for (i in 0 until 5) {
            b.drop(0)
        }
        Assertions.assertEquals(listOf('X', 'Y', 'X', 'Y', 'X'), b.board[0])
    }

    @Test
    fun testDropThrowsExceptionForInvalidColumnNumber() {
        val b = Board(5, 1)
        Assertions.assertThrows(InvalidMoveException::class.java) { b.drop(1) }
    }

    @Test
    fun testDropThrowsExceptionIfColumnFull() {
        val b = Board(5, 1)
        for (i in 0 until 5) {
            b.drop(0)
        }
        Assertions.assertThrows(InvalidMoveException::class.java) { b.drop(0) }
    }
}