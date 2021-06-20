package io.github.ruixuantan.rocknroll.server.domain.stats

import cats.Applicative
import cats.implicits.toTraverseOps

class StatsService[F[_]: Applicative](
    dieCountRepository: DieCountRepositoryAlgebra[F],
    resultsRepository: ResultsRepositoryAlgebra[F],
) {
  def listDieCount(): F[Array[DieCount]] =
    dieCountRepository.list()

  def getAllResults() = ???

  def createDieCount(dice: Array[DieCount]): F[List[DieCount]] =
    dice.toList.map(dieCountRepository.upsert).sequence

  def createResults(results: Results): F[Results] =
    resultsRepository.create(results)
}

object StatsService {
  def apply[F[_]: Applicative](
      dieCountRepository: DieCountRepositoryAlgebra[F],
      resultsRepository: ResultsRepositoryAlgebra[F],
  ) =
    new StatsService[F](dieCountRepository, resultsRepository)
}
