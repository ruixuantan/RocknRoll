package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.tokens.Value.Number

class NumberService {
  def getResult(number: Number): Result =
    Result(number.number, number.number)
}

object NumberService {
  def apply(): NumberService = new NumberService()
}
