package io.github.ruixuantan.rocknroll.core

import io.github.ruixuantan.rocknroll.core.parser.ParseError
import io.github.ruixuantan.rocknroll.core.tokens.{Result, Token}

trait CoreAlgebra {
  def parse(input: String): Either[ParseError, List[Token]]

  def validate(tokens: List[Token]): Boolean

  def eval(tokens: List[Token]): Either[ParseError, List[Result]]

  def prettyPrint(tokens: List[Token]): String
}
