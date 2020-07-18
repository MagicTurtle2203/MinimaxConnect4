import connect4.*

fun main() {
    val game = GameRunner(Modes.PVP, 7, 6)
    game.run()
}