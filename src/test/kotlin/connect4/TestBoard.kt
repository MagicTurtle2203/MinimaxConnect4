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
}