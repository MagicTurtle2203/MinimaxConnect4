import com.xenomachina.argparser.ArgParser
import argparser.Connect4Args
import connect4.*

fun main(args: Array<String>) {
    ArgParser(args).parseInto(::Connect4Args).run {
        val gameMode = when (mode) {
            "default" -> Modes.DEFAULT
            "pvp" -> Modes.PVP
            "aionly" -> Modes.AIONLY
            else -> throw SomethingWentWrong("This shouldn't happen")
        }
        val game = GameRunner(gameMode, numCols, numRows, lengthToWin, popOut)
        game.run()
    }
}