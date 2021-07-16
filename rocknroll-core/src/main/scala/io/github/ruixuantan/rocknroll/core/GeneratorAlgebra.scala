package io.github.ruixuantan.rocknroll.core

import io.github.ruixuantan.rocknroll.core.parser.{FinalResult, ParseError}
import io.github.ruixuantan.rocknroll.core.tokens.Token

trait GeneratorAlgebra {
  def getGeneratorName: String

  def eval(tokens: List[Token]): Either[ParseError, List[FinalResult]]
}
