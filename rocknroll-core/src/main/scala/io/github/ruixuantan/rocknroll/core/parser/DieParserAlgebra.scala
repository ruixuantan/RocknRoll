package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.tokens.{Result, Token}

trait DieParserAlgebra {
  def parse(input: String): Either[ParseError, List[Token]]

  def eval(tokens: List[Token]): Either[ParseError, List[Result]]
}
