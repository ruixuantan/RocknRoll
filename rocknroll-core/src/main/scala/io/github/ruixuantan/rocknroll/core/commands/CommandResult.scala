package io.github.ruixuantan.rocknroll.core.commands

sealed trait CommandResult
case class InvalidCommandResult(
    msg: String,
) extends CommandResult
case class DieCommandResult(
    rolls: List[Int],
    expected: List[Double],
    probabilities: List[Double],
) extends CommandResult
