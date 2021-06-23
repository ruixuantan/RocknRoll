package io.github.ruixuantan.rocknroll.core

import io.github.ruixuantan.rocknroll.core.parser.{FinalResult, ParseError}
import io.github.ruixuantan.rocknroll.core.tokens.Token
import io.github.ruixuantan.rocknroll.core.tokens.Value.Die

trait CoreAlgebra {
  def parse(input: String): Either[ParseError, List[Token]]

  def validate(tokens: List[Token]): Boolean

  def eval(tokens: List[Token]): Either[ParseError, List[FinalResult]]

  def prettyPrint(tokens: List[Token], delimiter: String = " "): String

  def getDice(tokens: List[Token]): List[Die]
}
