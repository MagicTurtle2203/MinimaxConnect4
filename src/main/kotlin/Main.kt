import connect4.*

fun main() {
    println(Int.MAX_VALUE)
    println(Int.MIN_VALUE)
    val game = GameRunner(Modes.DEFAULT, 7, 6)
    game.run()
}