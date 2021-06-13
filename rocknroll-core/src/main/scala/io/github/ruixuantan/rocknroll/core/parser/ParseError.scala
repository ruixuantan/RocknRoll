package io.github.ruixuantan.rocknroll.core.parser

sealed trait ParseError extends Product with Serializable { val msg: String }
case object ParseTokenError extends ParseError {
  val msg: String = "Invalid token passed"
}
case object ParseOrderError extends ParseError {
  val msg: String = "Ordering of tokens is wrong"
}
