package io.github.ruixuantan.rocknroll.core.tokens

sealed trait Token

sealed trait Value extends Token
object Value {
  case class Die(sides: Int, frequency: Int) extends Value
  case class Number(number: Int)             extends Value
}

sealed trait Operator extends Token
object Operator {
  case object Add      extends Operator
  case object Subtract extends Operator
  case object Separate extends Operator
}
