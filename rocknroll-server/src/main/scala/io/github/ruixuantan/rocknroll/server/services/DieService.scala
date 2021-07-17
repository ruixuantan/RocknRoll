package io.github.ruixuantan.rocknroll.server.services

import cats.Applicative
import cats.implicits._
import io.circe.generic.auto._
import io.circe.syntax.EncoderOps
import io.github.ruixuantan.rocknroll.core.{CoreAlgebra, GeneratorAlgebra, GeneratorService}
import io.github.ruixuantan.rocknroll.core.generators.DefaultGenerator
import io.github.ruixuantan.rocknroll.core.parser.ParseError
import io.github.ruixuantan.rocknroll.core.tokens.Token
import io.github.ruixuantan.rocknroll.server.models.{DieCount, Results}
import io.github.ruixuantan.rocknroll.server.routes.responses.{
  DieResponse,
  InvalidResponse,
  ValidResponse,
  ValidateResponse,
}
import io.github.ruixuantan.rocknroll.server.routes.{RoutePaths, RouteSuffixes}
import io.github.ruixuantan.rocknroll.server.utils.ActionableResponse

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DieService[F[_]: Applicative](
    coreAlgebra: CoreAlgebra,
    generatorMap: Map[String, GeneratorAlgebra],
    baseUrl: String,
) {

  def validate(input: String): F[DieResponse] = {
    val tokens = for {
      tokens <- coreAlgebra.parse(input)
    } yield tokens

    val response: DieResponse = tokens match {
      case Right(t) =>
        ValidateResponse(coreAlgebra.validate(t), coreAlgebra.prettyPrint(t))
      case Left(_) => ValidateResponse(isValid = false, input)
    }
    response.pure[F]
  }

  private def saveDieCount(
      tokens: Either[ParseError, List[Token]],
  ): Future[Unit] =
    Future {
      val body = for {
        t <- tokens
      } yield coreAlgebra
        .getDice(t)
        .map(die => DieCount(die.sides, die.frequency))
        .asJson
        .toString
      for {
        b <- body
      } yield HttpService.post(baseUrl + RoutePaths.StatsRoutePath.path + RouteSuffixes.statsDiecount, b)
    }

  private def saveResult(input: String, results: String, generator: String): Future[Unit] =
    Future {
      HttpService.post(
        baseUrl + RoutePaths.StatsRoutePath.path + RouteSuffixes.statsResult,
        Results(input = input, result = results, generator = generator).asJson.toString,
      )
    }

  def eval(input: String, generator: String): F[ActionableResponse[DieResponse]] = {
    val tokens = for {
      tokens <- coreAlgebra.parse(input)
    } yield tokens

    val generatorService: GeneratorAlgebra =
      generatorMap.getOrElse(generator, GeneratorService(DefaultGenerator()))

    val results = for {
      t   <- tokens
      res <- generatorService.eval(t)
    } yield res

    val response: ActionableResponse[DieResponse] = results match {
      case Right(res) =>
        val inputString  = res.map(_.input).mkString(" / ")
        val resultString = res.map(_.result).mkString(" / ")
        ActionableResponse(
          ValidResponse(
            inputString,
            resultString,
            res.map(_.expected).mkString(" / "),
            res.map(_.standardDeviation).mkString(" / "),
            res.toArray,
          ),
          List(
            () => saveDieCount(tokens),
            () => saveResult(inputString, resultString, generator),
          ),
        )
      case Left(err) => ActionableResponse(InvalidResponse(err.msg), null)
    }
    response.pure[F]
  }
}

object DieService {
  def apply[F[_]: Applicative](
      coreAlgebra: CoreAlgebra,
      generatorMaps: Map[String, GeneratorAlgebra],
      baseUrl: String,
  ) =
    new DieService[F](coreAlgebra, generatorMaps, baseUrl)
}
