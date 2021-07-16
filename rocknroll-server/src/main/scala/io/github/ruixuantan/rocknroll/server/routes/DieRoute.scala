package io.github.ruixuantan.rocknroll.server.routes

import cats.effect.Sync
import cats.implicits._
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
import io.github.ruixuantan.rocknroll.server.routes.requests.DieRequest
import io.github.ruixuantan.rocknroll.server.routes.responses.{InvalidResponse, ValidResponse, ValidateResponse}
import io.github.ruixuantan.rocknroll.server.services.DieService
import org.http4s.{EntityDecoder, HttpRoutes}
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.jsonOf
import org.http4s.dsl.Http4sDsl
import org.http4s.server.middleware.CORS

class DieRoute[F[_]: Sync] extends Http4sDsl[F] {

  implicit val dieRequestDecoder: EntityDecoder[F, DieRequest] = jsonOf[F, DieRequest]

  private def validateEndPoint(dieService: DieService[F]): HttpRoutes[F] =
    HttpRoutes.of { case req @ POST -> Root / RouteSuffixes.dieValidate =>
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
        dieReq <- req.as[DieRequest]
        res <- dieService
          .eval(dieReq)
          .flatMap { response =>
            response.dto match {
              case valid: ValidResponse =>
                response.actions.foreach(f => f())
                Ok(valid)
              case invalid: InvalidResponse => BadRequest(invalid.msg)
              case _                        => InternalServerError()
            }
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
