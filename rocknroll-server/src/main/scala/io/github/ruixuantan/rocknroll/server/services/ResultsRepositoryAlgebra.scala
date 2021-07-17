package io.github.ruixuantan.rocknroll.server.services

import io.github.ruixuantan.rocknroll.server.models.Results

trait ResultsRepositoryAlgebra[F[_]] {
  def create(results: Results): F[Results]

  def list: F[Array[Results]]
}
