package io.github.ruixuantan.rocknroll.server.services

import cats.Applicative
import cats.implicits.toTraverseOps
import io.github.ruixuantan.rocknroll.server.models.{DieCount, Results}

class StatsService[F[_]: Applicative](
    dieCountRepository: DieCountRepositoryAlgebra[F],
    resultsRepository: ResultsRepositoryAlgebra[F],
) {
  def listDieCount(): F[Array[DieCount]] =
    dieCountRepository.list()

  def getTopDieCount(count: Int): F[Array[DieCount]] =
    dieCountRepository.listTop(count)

  def getTotalDieCount(): F[Option[Int]] =
    dieCountRepository.getDieCountSum()

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
