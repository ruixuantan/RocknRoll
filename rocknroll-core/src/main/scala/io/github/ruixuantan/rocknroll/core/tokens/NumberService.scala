package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.results.Result
import io.github.ruixuantan.rocknroll.core.tokens.Value.Number

class NumberService extends ValueAlgebra[Number] {
  override def getResult(number: Number): Result =
    Result(number.number, number.number, 0)
}

object NumberService {
  def apply(): NumberService = new NumberService()
}
