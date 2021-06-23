package io.github.ruixuantan.rocknroll.core.results

import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}
import io.github.ruixuantan.rocknroll.core.tokens.{Value, ValueAlgebra}

class ResultService(
    dieAlgebra: ValueAlgebra[Die],
    numberAlgebra: ValueAlgebra[Number],
) extends ResultAlgebra {
  override def identity: Result = Result(0, 0, 0)

  override def getResult(x: Value): Result =
    x match {
      case die: Die       => dieAlgebra.getResult(die)
      case number: Number => numberAlgebra.getResult(number)
    }

  override def add(x: Result)(y: Result): Result =
    Result(
      x.result + y.result,
      x.expected + y.expected,
      x.variance + y.variance,
    )

  override def subtract(x: Result)(y: Result): Result =
    Result(
      x.result - y.result,
      x.expected - y.expected,
      x.variance + y.variance,
    )
}

object ResultService {
  def apply(
      dieAlgebra: ValueAlgebra[Die],
      numberAlgebra: ValueAlgebra[Number],
  ): ResultService =
    new ResultService(dieAlgebra, numberAlgebra)
}
