package io.github.ruixuantan.rocknroll.server.domain.stats

trait ResultsRepositoryAlgebra[F[_]] {
  def create(results: Results): F[Results]

  def list(): F[Array[Results]]
}
