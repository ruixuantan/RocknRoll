package io.github.ruixuantan.rocknroll.server.services

import io.github.ruixuantan.rocknroll.server.models.DieCount

trait DieCountRepositoryAlgebra[F[_]] {
  def upsert(die: DieCount): F[DieCount]

  def get(dieSides: Int): F[Option[DieCount]]

  def list: F[Array[DieCount]]

  def listTop(count: Int): F[Array[DieCount]]

  def getDieCountSum: F[Option[Int]]
}
