package io.github.ruixuantan.rocknroll.server.infrastructure.routes

import cats.effect.Sync
import cats.implicits._
import io.github.ruixuantan.rocknroll.server.domain.stats.{
  DieCount,
  Results,
  StatsService,
}
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
import io.circe.syntax.EncoderOps
import org.http4s.{EntityDecoder, HttpRoutes}
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.jsonOf
import org.http4s.dsl.Http4sDsl
import org.http4s.server.middleware.CORS

class StatsRoute[F[_]: Sync] extends Http4sDsl[F] {

  implicit val dieCountDecoder: EntityDecoder[F, Array[DieCount]] =
    jsonOf[F, Array[DieCount]]
  implicit val resultsDecoder: EntityDecoder[F, Results] = jsonOf[F, Results]

  private def dieCountEndpoint(statsService: StatsService[F]): HttpRoutes[F] =
    HttpRoutes.of { case GET -> Root =>
      for {
        count <- statsService.listDieCount()
        resp  <- Ok(count.asJson)
      } yield resp
    }

  private def createDieCountEndpoint(
      statsService: StatsService[F],
  ): HttpRoutes[F] =
    HttpRoutes.of { case req @ POST -> Root / "diecount" =>
      for {
        dice <- req.as[Array[DieCount]]
        resp <- Ok(statsService.createDieCount(dice))
      } yield resp
    }

  private def createResultsEndpoint(
      statsService: StatsService[F],
  ): HttpRoutes[F] =
    HttpRoutes.of { case req @ POST -> Root / "results" =>
      for {
        res     <- req.as[Results]
        results <- Ok(statsService.createResults(res))
      } yield results
    }

  def endPoints(statsService: StatsService[F]): HttpRoutes[F] =
    CORS(dieCountEndpoint(statsService)) <+>
      createDieCountEndpoint(statsService) <+>
      createResultsEndpoint(statsService)
}

object StatsRoute {
  def endpoints[F[_]: Sync](statsService: StatsService[F]): HttpRoutes[F] =
    new StatsRoute[F]().endPoints(statsService)
}
