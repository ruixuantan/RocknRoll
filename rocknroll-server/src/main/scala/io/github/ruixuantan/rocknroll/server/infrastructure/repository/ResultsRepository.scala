package io.github.ruixuantan.rocknroll.server.infrastructure.repository

import cats.effect.Sync
import doobie.{LogHandler, Query0, Transactor, Update0}
import doobie.implicits._
import io.github.ruixuantan.rocknroll.server.domain.stats.{
  Results,
  ResultsRepositoryAlgebra,
}

private object ResultsSQLService {
  def insert(results: Results): Update0 =
    sql"""INSERT INTO results (input_string, result) VALUES (${results.input}, ${results.result})"""
      .updateWithLogHandler(LogHandler.jdkLogHandler)

  def selectAll(): Query0[Results] =
    sql"""SELECT * FROM results ORDER BY id""".query
}

class ResultsRepository[F[_]: Sync](val xa: Transactor[F])
    extends ResultsRepositoryAlgebra[F] {
  import ResultsSQLService._

  implicit val handler: LogHandler = LogHandler.jdkLogHandler

  override def create(results: Results): F[Results] =
    insert(results)
      .withUniqueGeneratedKeys[Int]("id")
      .map(id => results.copy(id = id))
      .transact(xa)

  override def list(): F[Array[Results]] =
    selectAll().to[Array].transact(xa)
}

object ResultsRepository {
  def apply[F[_]: Sync](xa: Transactor[F]) = new ResultsRepository[F](xa)
}
