package io.github.ruixuantan.rocknroll.server.services

import cats.Applicative
import cats.implicits._
import io.circe.generic.auto._
import io.circe.syntax.EncoderOps
import io.github.ruixuantan.rocknroll.core.CoreAlgebra
import io.github.ruixuantan.rocknroll.core.parser.ParseError
import io.github.ruixuantan.rocknroll.core.tokens.Token
import io.github.ruixuantan.rocknroll.server.dto.{
  DieResponse,
  InvalidResponse,
  ValidResponse,
  ValidateResponse,
}
import io.github.ruixuantan.rocknroll.server.models.{DieCount, Results}
import io.github.ruixuantan.rocknroll.server.routes.{RoutePaths, RouteSuffixes}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DieService[F[_]: Applicative](
    coreAlgebra: CoreAlgebra,
    baseUrl: String,
) {
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

  private def saveDieCount(
      tokens: Either[ParseError, List[Token]],
  ): Future[_] =
    Future {
      val body = for {
        t <- tokens
      } yield coreAlgebra
        .getDice(t)
        .map(die => DieCount(die.sides, die.freq))
        .asJson
        .toString
      for {
        b <- body
      } yield HttpService.post(
        baseUrl + RoutePaths.StatsRoutePath.path + RouteSuffixes.statsDiecount,
        b,
      )
    }

  private def saveResult(input: String, results: String): Future[_] =
    Future {
      HttpService.post(
        baseUrl + RoutePaths.StatsRoutePath.path + RouteSuffixes.statsResult,
        Results(input = input, result = results).asJson.toString,
      )
    }

  def eval(input: String): F[DieResponse] = {
    val tokens = for {
      tokens <- coreAlgebra.parse(input)
    } yield tokens

    val results = for {
      t   <- tokens
      res <- coreAlgebra.eval(t)
    } yield res

    val response: DieResponse = results match {
      case Right(res) =>
        val validResponse = ValidResponse(
          res.map(_.res).mkString(" / "),
          res.map(_.expected).mkString(" / "),
        )
        saveDieCount(tokens)
        saveResult(input, validResponse.results)
        validResponse
      case Left(err) => InvalidResponse(err.msg)
    }
    response.pure[F]
  }
}

object DieService {
  def apply[F[_]: Applicative](
      coreAlgebra: CoreAlgebra,
      baseUrl: String,
  ) =
    new DieService[F](coreAlgebra, baseUrl)
}
