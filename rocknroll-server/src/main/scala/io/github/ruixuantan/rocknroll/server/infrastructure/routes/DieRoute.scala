package io.github.ruixuantan.rocknroll.server.infrastructure.routes

import cats.effect.Sync
import cats.implicits._
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
import io.github.ruixuantan.rocknroll.server.domain.dice.{
  DieService,
  InvalidResponse,
  ValidResponse,
  ValidateResponse,
}
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.middleware.CORS

class DieRoute[F[_]: Sync] extends Http4sDsl[F] {

  private def validateEndPoint(dieService: DieService[F]): HttpRoutes[F] =
    HttpRoutes.of { case req @ POST -> Root / "validate" =>
      for {
        msg <- req.as[String]
        res <- dieService
          .validate(msg)
          .flatMap {
            case response: ValidateResponse => Ok(response)
            case _                          => InternalServerError()
          }
      } yield res
    }

  def evalEndPoint(dieService: DieService[F]): HttpRoutes[F] =
    HttpRoutes.of { case req @ POST -> Root =>
      for {
        msg <- req.as[String]
        res <- dieService
          .eval(msg)
          .flatMap {
            case response: ValidResponse   => Ok(response)
            case response: InvalidResponse => BadRequest(response.msg)
            case _                         => InternalServerError()
          }
      } yield res
    }

  def endPoints(dieService: DieService[F]): HttpRoutes[F] =
    CORS(validateEndPoint(dieService)) <+> CORS(evalEndPoint(dieService))
}

object DieRoute {
  def endpoints[F[_]: Sync](dieService: DieService[F]): HttpRoutes[F] =
    new DieRoute[F]().endPoints(dieService)
}
