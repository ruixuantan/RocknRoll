package io.github.ruixuantan.rocknroll.core.commands

import io.github.ruixuantan.rocknroll.core.dice.{Die, DieService}

class CommandService(dieService: DieService) {
  private def executeInvalidCommand(msg: String): CommandResult =
    InvalidCommandResult(msg)

  private def executeRollCommand(dice: List[Die]): CommandResult = {
    val results = dice
      .map(die => dieService.roll(die))
      .map(res => (res.result, res.expected, res.probability))
      .unzip3
    DieCommandResult(results._1, results._2, results._3)
  }

  def execute(command: CommandsEnum): CommandResult =
    command match {
      case rollCmd: RollCommand       => executeRollCommand(rollCmd.dice)
      case invalidCmd: InvalidCommand => executeInvalidCommand(invalidCmd.msg)
    }
}

object CommandService {
  def apply(dieService: DieService): CommandService = new CommandService(
    dieService,
  )
}
