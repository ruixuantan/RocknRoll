package io.github.ruixuantan.rocknroll.server.domain.dice

import cats.Applicative
import cats.implicits._
import io.github.ruixuantan.rocknroll.core.Service

class DieService[F[_]: Applicative] {
  def parse(input: String): F[DieResponse] = {
    val response: DieResponse = Service.execute(input) match {
      case Right(res) =>
        ValidResponse(
          res.res,
          res.expected,
        )
      case Left(err) => InvalidResponse(err.msg)
    }
    response.pure[F]
  }
}

object DieService {
  def apply[F[_]: Applicative]() =
    new DieService[F]()
}
