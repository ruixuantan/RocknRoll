package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.commands.{
  CommandsEnum,
  InvalidCommand,
  RollCommand,
}

class ParserService {
  private def parseRollCommand(input: String): CommandsEnum =
    RollParserService().parse(input) match {
      case Left(_)     => InvalidCommand("Cannot parse input string number")
      case Right(dice) => RollCommand(dice)
    }

  def parse(input: String): CommandsEnum =
    input match {
      case RollParser.syntax(_*) => parseRollCommand(input)
      case InvalidParser.syntax(_*) =>
        InvalidCommand("Cannot parse input string")
    }
}

object ParserService {
  def apply() =
    new ParserService()
}
