package io.github.ruixuantan.rocknroll.server.domain.stats

trait DieCountRepositoryAlgebra[F[_]] {
  def upsert(die: DieCount): F[DieCount]

  def get(dieSides: Int): F[Option[DieCount]]

  def list(): F[Array[DieCount]]
}
