package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.generators.Generator
import io.github.ruixuantan.rocknroll.core.tokens.Token

trait DieParserAlgebra {
  def parse(input: String): Either[ParseError, List[Token]]

  def validate(tokens: List[Token]): Boolean

  def eval(tokens: List[Token], generator: Generator): Either[ParseError, List[FinalResult]]
}
