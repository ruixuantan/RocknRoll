package io.github.ruixuantan.rocknroll.core

import io.github.ruixuantan.rocknroll.core.parser.{
  DieParserService,
  ParseError,
  TokenParser,
}
import io.github.ruixuantan.rocknroll.core.results.{Result, ResultService}
import io.github.ruixuantan.rocknroll.core.tokens.{
  DieService,
  NumberService,
  Token,
}

object CoreService extends CoreAlgebra {

  val diceService      = DieService()
  val numberService    = NumberService()
  val resultService    = ResultService(diceService, numberService)
  val tokenParser      = TokenParser()
  val dieParserService = DieParserService(tokenParser, resultService)

  override def parse(input: String): Either[ParseError, List[Token]] =
    dieParserService.parse(input)

  override def validate(tokens: List[Token]): Boolean =
    dieParserService.validate(tokens)

  override def eval(tokens: List[Token]): Either[ParseError, List[Result]] =
    dieParserService.eval(tokens)

  override def prettyPrint(
      tokens: List[Token],
      delimiter: String,
  ): String =
    tokens.map(token => tokenParser.prettyPrintToken(token)).mkString(delimiter)
}
