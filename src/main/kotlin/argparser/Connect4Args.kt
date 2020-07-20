package argparser

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.InvalidArgumentException
import com.xenomachina.argparser.default

class Connect4Args(parser: ArgParser) {
    val mode by parser.storing("-m", "--m",
            help = "game modes: default -> player vs AI, pvp -> player vs player, " +
                    "aionly -> AI vs AI") { toLowerCase() }
            .default("default")
            .addValidator {
                if (value !in listOf<String>("default", "pvp", "aionly"))
                    throw InvalidArgumentException("Please choose a valid game mode")
            }

    val numCols by parser.storing("-c", "--columns",
            help = "number of columns") { toInt() }
            .default(7)

    val numRows by parser.storing("-r", "--rows",
            help = "number of rows") { toInt() }
            .default(6)

    val lengthToWin by parser.storing("-l", "--length",
            help = "number of tokens in a row needed to win") { toInt() }
            .default(4)
            .addValidator {
                if (value > numCols || value > numRows) {
                    throw InvalidArgumentException("Length to win cannot be longer than board size")
                }
            }

    val popOut by parser.flagging("-p", "--popout",
            help = "enable popout mode")
}