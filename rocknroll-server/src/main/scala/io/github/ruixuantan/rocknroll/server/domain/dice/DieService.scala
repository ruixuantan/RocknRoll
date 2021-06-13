package io.github.ruixuantan.rocknroll.server.domain.dice

import cats.Applicative
import cats.implicits._
import io.github.ruixuantan.rocknroll.core.Service

class DieService[F[_]: Applicative] {
  def validate(input: String): F[DieResponse] = {
    val response: DieResponse = Service.execute(input) match {
      case Right(_) =>
        ValidateResponse(
          true,
          Service.parse(input) match {
            case Right(tokens) => Service.prettyPrint(tokens)
            case Left(_)       => input
          },
        )
      case Left(_) => ValidateResponse(false, input)
    }
    response.pure[F]
  }

  def eval(input: String): F[DieResponse] = {
    val response: DieResponse = Service.execute(input) match {
      case Right(res) =>
        ValidResponse(
          res.map(_.res).mkString(" / "),
          res.map(_.expected).mkString(" / "),
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
