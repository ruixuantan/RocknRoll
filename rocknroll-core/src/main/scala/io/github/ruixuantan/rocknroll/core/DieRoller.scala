package io.github.ruixuantan.rocknroll.core

import io.github.ruixuantan.rocknroll.core.commands.{
  CommandResult,
  CommandService,
}
import io.github.ruixuantan.rocknroll.core.dice.DieService
import io.github.ruixuantan.rocknroll.core.parser.ParserService

object DieRoller {
  def execute(input: String): CommandResult = {
    val dieService     = DieService()
    val parserService  = ParserService()
    val commandService = CommandService(dieService)
    val parseFunction =
      (parserService.parse _).andThen(commandService.execute _)
    parseFunction(input)
  }
}
