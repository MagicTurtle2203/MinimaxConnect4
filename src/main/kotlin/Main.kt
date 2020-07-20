import connect4.*

fun main() {
    val game = GameRunner(Modes.DEFAULT, 7, 6)
    game.run()
}