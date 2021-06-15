package io.github.ruixuantan.rocknroll.server.domain.dice

import cats.Applicative
import cats.implicits._
import io.github.ruixuantan.rocknroll.core.CoreAlgebra

class DieService[F[_]: Applicative](coreAlgebra: CoreAlgebra) {
  def validate(input: String): F[DieResponse] = {
    val tokens = for {
      tokens <- coreAlgebra.parse(input)
    } yield tokens

    val response: DieResponse = tokens match {
      case Right(t) =>
        ValidateResponse(
          coreAlgebra.validate(t),
          coreAlgebra.prettyPrint(t),
        )
      case Left(_) => ValidateResponse(isValid = false, input)
    }
    response.pure[F]
  }

  def eval(input: String): F[DieResponse] = {
    val results = for {
      tokens <- coreAlgebra.parse(input)
      res    <- coreAlgebra.eval(tokens)
    } yield res

    val response: DieResponse = results match {
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
  def apply[F[_]: Applicative](coreAlgebra: CoreAlgebra) =
    new DieService[F](coreAlgebra)
}
