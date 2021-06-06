package io.github.ruixuantan.rocknroll.server.infrastructure.routes

import cats.effect.Sync
import cats.implicits._
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
import io.github.ruixuantan.rocknroll.server.domain.dice.{
  DieService,
  InvalidResponse,
  ValidResponse,
}
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.Http4sDsl

class DieRoute[F[_]: Sync] extends Http4sDsl[F] {

  def endpoints(dieService: DieService[F]): HttpRoutes[F] =
    HttpRoutes.of { case req @ POST -> Root =>
      for {
        msg <- req.as[String]
        res <- dieService
          .parse(msg)
          .flatMap(res =>
            res match {
              case response: InvalidResponse => BadRequest(response.msg)
              case response: ValidResponse   => Ok(response)
            },
          )
      } yield res
    }
}

object DieRoute {
  def endpoints[F[_]: Sync](dieService: DieService[F]): HttpRoutes[F] =
    new DieRoute[F]().endpoints(dieService)
}
