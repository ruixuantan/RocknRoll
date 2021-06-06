package io.github.ruixuantan.rocknroll.server.domain.dice

import cats.Applicative
import cats.implicits._
import io.github.ruixuantan.rocknroll.core.{DieMsgContainer, DieRoller}

class DieService[F[_]: Applicative] {
  def parse(input: String): F[DieMsgContainer] =
    DieRoller.execute(input).pure[F]
}

object DieService {
  def apply[F[_]: Applicative]() =
    new DieService[F]()
}
