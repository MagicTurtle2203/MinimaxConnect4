package connect4

enum class MoveType {
    DROP, POP
}

data class Move(val moveType: MoveType, val columnIndex: Int)