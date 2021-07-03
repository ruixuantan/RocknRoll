package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.results.Result
import io.github.ruixuantan.rocknroll.core.tokens.Value.Die
import io.github.ruixuantan.rocknroll.core.tokens.Value.Number

trait ValueAlgebra[V] {
  def getResult(value: V): Result
}

object ValueInstances {
  implicit val valueAlgebraInterpreter: ValueAlgebra[Value] =
    new ValueAlgebra[Value] {
      override def getResult(value: Value): Result = value match {
        case die: Die       => dieAlgebraInterpreter.getResult(die)
        case number: Number => numberAlgebraInterpreter.getResult(number)
      }
    }

  implicit val dieAlgebraInterpreter: ValueAlgebra[Die] =
    DieAlgebraInterpreter()

  implicit val numberAlgebraInterpreter: ValueAlgebra[Number] =
    NumberAlgebraInterpreter()
}

object ValueSyntax {
  implicit class ValueAlgebraOps[V](value: V) {
    def getResult(implicit interpreter: ValueAlgebra[V]): Result =
      interpreter.getResult(value)
  }
}
