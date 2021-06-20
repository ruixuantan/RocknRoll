package io.github.ruixuantan.rocknroll.server.repository

import cats.effect.Sync
import doobie.implicits._
import doobie.{LogHandler, Query0, Transactor, Update0}
import io.github.ruixuantan.rocknroll.server.models.DieCount
import io.github.ruixuantan.rocknroll.server.services.DieCountRepositoryAlgebra

private object DieCountSQLService {
  implicit val handler = LogHandler.jdkLogHandler

  def upsertDie(die: DieCount): Update0 =
    sql"""INSERT INTO die_count (die_side, frequency) VALUES (${die.sides}, ${die.freq}) 
        ON CONFLICT (die_side) DO UPDATE SET frequency = die_count.frequency + ${die.freq}
       """.update

  def selectByDieSides(dieSides: Int): Query0[DieCount] =
    sql"""SELECT * FROM die_count WHERE die_side=$dieSides""".query

  def selectAll: Query0[DieCount] =
    sql"""SELECT * FROM die_count ORDER BY die_side""".queryWithLogHandler(
      LogHandler.jdkLogHandler,
    )
}

class DieCountRepository[F[_]: Sync](val xa: Transactor[F])
    extends DieCountRepositoryAlgebra[F] {
  import DieCountSQLService._
  implicit val handler = LogHandler.jdkLogHandler

  override def upsert(die: DieCount): F[DieCount] =
    upsertDie(die).run.map(_ => die).transact(xa)

  override def get(dieSides: Int): F[Option[DieCount]] =
    selectByDieSides(dieSides).option.transact(xa)

  override def list(): F[Array[DieCount]] =
    selectAll.to[Array].transact(xa)
}

object DieCountRepository {
  def apply[F[_]: Sync](xa: Transactor[F]) = new DieCountRepository[F](xa)
}
