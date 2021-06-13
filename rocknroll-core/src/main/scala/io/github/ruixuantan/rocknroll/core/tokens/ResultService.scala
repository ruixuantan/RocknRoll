package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}

class ResultService(dieService: DieService, numberService: NumberService)
    extends ResultAlgebra {
  override def identity(): Result = Result(0, 0)

  override def getResult(x: Value): Result =
    x match {
      case die: Die       => dieService.roll(die)
      case number: Number => numberService.getResult(number)
    }

  override def add(x: Result)(y: Result): Result =
    Result(x.res + y.res, x.expected + y.expected)

  override def subtract(x: Result)(y: Result): Result =
    Result(x.res - y.res, x.expected - y.expected)
}

object ResultService {
  def apply(
      dieService: DieService,
      numberService: NumberService,
  ): ResultService =
    new ResultService(dieService, numberService)
}
