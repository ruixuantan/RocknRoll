package io.github.ruixuantan.rocknroll.core.commands

import io.github.ruixuantan.rocknroll.core.dice.Die

sealed trait CommandsEnum
case object InvalidCommand              extends CommandsEnum
case class RollCommand(dice: List[Die]) extends CommandsEnum
