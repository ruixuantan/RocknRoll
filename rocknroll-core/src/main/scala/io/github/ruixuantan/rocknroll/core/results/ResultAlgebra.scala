package io.github.ruixuantan.rocknroll.core.results

trait ResultAlgebra {
  def identity: Result

  def add(x: Result)(y: Result): Result

  def subtract(x: Result)(y: Result): Result
}

object ResultInstances {
  implicit val resultAlgebraInterpreter: ResultAlgebra =
    ResultAlgebraInterpreter()
}

object ResultSyntax {
  implicit class ResultAlgebraOps(res: Result) {
    def identity(implicit interpreter: ResultAlgebra): Result =
      interpreter.identity

    def +(other: Result)(implicit interpreter: ResultAlgebra): Result =
      interpreter.add(res)(other)

    def -(other: Result)(implicit interpreter: ResultAlgebra): Result =
      interpreter.subtract(res)(other)
  }
}
