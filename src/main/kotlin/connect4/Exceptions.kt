package connect4

class InvalidMoveException(message: String) : Exception(message)

class InvalidGameSettings(message: String) : Exception(message)

class SomethingWentWrong(message: String) : Exception(message)

class ExitGame(message: String) : Exception(message)