package io.github.ruixuantan.rocknroll.server

import cats.effect.{Async, Blocker, ConcurrentEffect, ContextShift, ExitCode, IO, IOApp, Resource, Sync, Timer}
import config.{DbConfig, DbMigrator, LogConfig, RocknRollConfig, ServerConfig}
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import io.circe.config.parser
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
import io.github.ruixuantan.rocknroll.core.{CoreService, GeneratorAlgebra, GeneratorService}
import io.github.ruixuantan.rocknroll.core.generators.{CyclicalGenerator, DefaultGenerator}
import io.github.ruixuantan.rocknroll.server.repository.{DieCountRepository, ResultsRepository}
import io.github.ruixuantan.rocknroll.server.routes.{DieRoute, RoutePaths, StatsRoute}
import io.github.ruixuantan.rocknroll.server.services.{DieService, StatsService}
import org.http4s.HttpRoutes
import org.http4s.server.{Router, Server => BlazeServer}
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger

import scala.concurrent.ExecutionContext.Implicits.global

object Server extends IOApp {
  private def config[F[_]: ContextShift: Sync]: Resource[F, RocknRollConfig] =
    Resource.eval(parser.decodePathF[F, RocknRollConfig]("rocknroll"))

  private def initialiseGeneratorServices: Map[String, GeneratorAlgebra] = {
    val cyclic  = GeneratorService(CyclicalGenerator(0))
    val default = GeneratorService(DefaultGenerator())
    Map(cyclic.getGeneratorName -> cyclic, default.getGeneratorName -> default)
  }

  private def loggedRoutes[F[_]: ConcurrentEffect](
      cfg: LogConfig,
      routes: HttpRoutes[F],
  ): HttpRoutes[F] =
    Logger.httpRoutes(cfg.httpheader, cfg.httpbody)(routes)

  private def dbTransactor[F[_]: Async: ContextShift](
      cfg: DbConfig,
  ): Resource[F, HikariTransactor[F]] = {
    implicit val cs: ContextShift[IO] =
      IO.contextShift(ExecutionContexts.synchronous)
    for {
      ce <- ExecutionContexts.fixedThreadPool(cfg.pool)
      be <- Blocker[F]
      tx <-
        HikariTransactor.newHikariTransactor(
          cfg.driver,
          cfg.url,
          cfg.user,
          cfg.password,
          ce,
          be,
        )
    } yield tx
  }

  private def dbMigrate[F[_]: Sync](cfg: DbConfig): Resource[F, Unit] =
    Resource.eval(DbMigrator.initializeDb(cfg))

  private def server[F[_]: ConcurrentEffect: Timer](
      cfg: ServerConfig,
      routes: HttpRoutes[F],
  ): Resource[F, BlazeServer[F]] = {
    import org.http4s.implicits._

    BlazeServerBuilder[F](global)
      .bindHttp(cfg.port, cfg.host)
      .withHttpApp(routes.orNotFound)
      .resource
  }

  def init[F[_]: ContextShift: ConcurrentEffect: Timer]: Resource[F, BlazeServer[F]] =
    for {
      conf <- config[F]
      _    <- dbMigrate[F](conf.db)
      xa   <- dbTransactor[F](conf.db)
      coreAlgebra        = CoreService
      generatorMap       = initialiseGeneratorServices
      dieCountRepository = DieCountRepository[F](xa)
      resultsRepository  = ResultsRepository[F](xa)
      baseUrl            = conf.server.baseUrl
      dieService         = DieService[F](coreAlgebra, generatorMap, baseUrl)
      statsService       = StatsService[F](dieCountRepository, resultsRepository)
      routes = Router(
        RoutePaths.DieRoutePath.path   -> DieRoute.endpoints[F](dieService),
        RoutePaths.StatsRoutePath.path -> StatsRoute.endpoints[F](statsService),
      )
      loggedRts = loggedRoutes(conf.log, routes)
      svr <- server[F](conf.server, loggedRts)
    } yield svr

  def run(args: List[String]): IO[ExitCode] =
    init.use(_ => IO.never).as(ExitCode.Success)
}
