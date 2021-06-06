package io.github.ruixuantan.rocknroll.server.config

import cats.effect.Sync
import cats.syntax.functor._
import org.flywaydb.core.Flyway

final case class DbConfig(
  driver: String,
  url: String,
  user: String,
  password: String,
  pool: Int
)

object DbMigrator {
  def initializeDb[F[_]](cfg: DbConfig)(implicit S: Sync[F]): F[Unit] =
    S.delay {
      val fw: Flyway = Flyway.configure()
        .dataSource(cfg.url, cfg.user, cfg.password)
        .load()
      fw.migrate()
    }.as(())
}