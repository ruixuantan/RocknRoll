package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.tokens.Operator.{
  Add,
  Separate,
  Subtract,
}
import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}

trait TokenAlgebra[T] {
  def prettyPrint(token: T): String
}

object TokenInstances {
  implicit val tokenAlgebraInterpreter: TokenAlgebra[Token] =
    new TokenAlgebra[Token] {
      override def prettyPrint(token: Token): String = token match {
        case die: Die =>
          if (die.frequency == 1) s"d${die.sides}"
          else s"${die.frequency}d${die.sides}"
        case number: Number => number.number.toString
        case Add            => "+"
        case Subtract       => "-"
        case Separate       => "/"
      }
    }
}

object TokenSyntax {
  implicit class TokenAlgebraOps[T](token: T) {
    def prettyPrint(implicit interpreter: TokenAlgebra[T]): String =
      interpreter.prettyPrint(token)
  }
}
