package io.github.ruixuantan.rocknroll.core.results

class ResultAlgebraInterpreter extends ResultAlgebra {
  def identity: Result = Result(0, 0, 0, 0, 0, 0)

  def add(x: Result)(y: Result): Result =
    Result(
      x.result + y.result,
      x.expected + y.expected,
      x.variance + y.variance,
      x.lowerBound + y.lowerBound,
      x.upperBound + y.upperBound,
      x.diceRolled + y.diceRolled,
    )

  def subtract(x: Result)(y: Result): Result =
    Result(
      x.result - y.result,
      x.expected - y.expected,
      x.variance + y.variance,
      x.lowerBound - y.upperBound,
      x.upperBound - y.lowerBound,
      x.diceRolled + y.diceRolled,
    )
}

object ResultAlgebraInterpreter {
  def apply(): ResultAlgebraInterpreter = new ResultAlgebraInterpreter()
}
