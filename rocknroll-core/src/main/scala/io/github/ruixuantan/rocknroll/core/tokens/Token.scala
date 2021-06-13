package io.github.ruixuantan.rocknroll.core.tokens

trait Token

trait Value extends Token
object Value {
  case class Die(sides: Int, freq: Int) extends Value
  case class Number(number: Int)        extends Value
}

trait Operator extends Token
object Operator {
  case object Add      extends Operator
  case object Subtract extends Operator
  case object Separate extends Operator
}
