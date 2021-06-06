package io.github.ruixuantan.rocknroll.server.domain.dice

import cats.Applicative
import cats.implicits._
import io.github.ruixuantan.rocknroll.core.DieRoller
import io.github.ruixuantan.rocknroll.core.commands.{
  DieCommandResult,
  InvalidCommandResult,
}

class DieService[F[_]: Applicative] {
  def parse(input: String): F[DieResponse] = {
    val response: DieResponse = DieRoller.execute(input) match {
      case invalid: InvalidCommandResult => InvalidResponse(invalid.msg)
      case valid: DieCommandResult =>
        ValidResponse(
          valid.rolls.mkString(", "),
          valid.expected.mkString(", "),
          valid.probabilities.mkString(", "),
        )
    }
    response.pure[F]
  }
}

object DieService {
  def apply[F[_]: Applicative]() =
    new DieService[F]()
}
