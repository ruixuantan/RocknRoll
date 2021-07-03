package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.results.Result
import io.github.ruixuantan.rocknroll.core.tokens.Value.Number

object NumberService {
  def getResult(number: Number): Result =
    Result(number.number, number.number, 0, number.number, number.number, 0)
}
