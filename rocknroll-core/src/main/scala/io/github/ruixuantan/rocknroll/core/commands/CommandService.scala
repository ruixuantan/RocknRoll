package io.github.ruixuantan.rocknroll.core.commands

import io.github.ruixuantan.rocknroll.core.dice.{Die, DieService}

class CommandService(dieService: DieService) {
  private def executeInvalidCommand(): CommandResult =
    CommandResult("Command is invalid")

  private def executeRollCommand(dice: List[Die]): CommandResult =
    CommandResult(
      dice
        .map(die => dieService.roll(die).toString)
        .fold("")((acc, elem) => acc + " " + elem)
        .substring(1),
    )

  def execute(command: CommandsEnum): CommandResult =
    command match {
      case rollCmd: RollCommand => executeRollCommand(rollCmd.dice)
      case InvalidCommand       => executeInvalidCommand()
    }
}

object CommandService {
  def apply(dieService: DieService): CommandService = new CommandService(
    dieService,
  )
}
