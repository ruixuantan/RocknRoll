package io.github.ruixuantan.rocknroll.server.routes

import cats.effect.Sync
import cats.implicits._
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
import io.circe.syntax.EncoderOps
import io.github.ruixuantan.rocknroll.server.models.{DieCount, Results}
import io.github.ruixuantan.rocknroll.server.routes.responses.DieCountSum
import io.github.ruixuantan.rocknroll.server.services.StatsService
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.jsonOf
import org.http4s.dsl.Http4sDsl
import org.http4s.server.middleware.CORS
import org.http4s.{EntityDecoder, HttpRoutes}

class StatsRoute[F[_]: Sync] extends Http4sDsl[F] {

  implicit val dieCountDecoder: EntityDecoder[F, Array[DieCount]] =
    jsonOf[F, Array[DieCount]]
  implicit val resultsDecoder: EntityDecoder[F, Results] = jsonOf[F, Results]
  object DieCountQueryParamMatcher extends QueryParamDecoderMatcher[Int]("count")

  private def getAllDieCountEndpoint(
      statsService: StatsService[F],
  ): HttpRoutes[F] =
    HttpRoutes.of { case GET -> Root =>
      for {
        dieCount <- statsService.listDieCount
        resp     <- Ok(dieCount.asJson)
      } yield resp
    }

  private def getDieCountEndpoint(
      statsService: StatsService[F],
  ): HttpRoutes[F] =
    HttpRoutes.of { case GET -> Root :? DieCountQueryParamMatcher(count) =>
      for {
        count <- statsService.getTopDieCount(count)
        resp  <- Ok(count.asJson)
      } yield resp
    }

  private def getDieCountTotalEndpoint(
      statsService: StatsService[F],
  ): HttpRoutes[F] =
    HttpRoutes.of { case GET -> Root / RouteSuffixes.statsDiecountSum =>
      for {
        total <- statsService.getTotalDieCount
        x <- total match {
          case Some(value) => Ok(DieCountSum(value))
          case None        => Ok(DieCountSum(0))
        }
      } yield x
    }

  private def createDieCountEndpoint(
      statsService: StatsService[F],
  ): HttpRoutes[F] =
    HttpRoutes.of { case req @ POST -> Root / RouteSuffixes.statsDiecount =>
      for {
        dice <- req.as[Array[DieCount]]
        resp <- Ok(statsService.createDieCount(dice))
      } yield resp
    }

  private def createResultsEndpoint(
      statsService: StatsService[F],
  ): HttpRoutes[F] =
    HttpRoutes.of { case req @ POST -> Root / RouteSuffixes.statsResult =>
      for {
        res     <- req.as[Results]
        results <- Ok(statsService.createResults(res))
      } yield results
    }

  def endPoints(statsService: StatsService[F]): HttpRoutes[F] =
    CORS(getAllDieCountEndpoint(statsService)) <+>
      CORS(getDieCountEndpoint(statsService)) <+>
      CORS(getDieCountTotalEndpoint(statsService)) <+>
      createDieCountEndpoint(statsService) <+>
      createResultsEndpoint(statsService)
}

object StatsRoute {
  def endpoints[F[_]: Sync](statsService: StatsService[F]): HttpRoutes[F] =
    new StatsRoute[F]().endPoints(statsService)
}
