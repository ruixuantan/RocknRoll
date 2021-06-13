package io.github.ruixuantan.rocknroll.core

import io.github.ruixuantan.rocknroll.core.parser.{
  DieParserService,
  ParseError,
  TokenParser,
}
import io.github.ruixuantan.rocknroll.core.tokens.{
  DieService,
  NumberService,
  Result,
  ResultService,
  Token,
}

object Service {

  val diceService      = DieService()
  val numberService    = NumberService()
  val resultService    = ResultService(diceService, numberService)
  val tokenParser      = TokenParser()
  val dieParserService = DieParserService(tokenParser, resultService)

  def parse(input: String): Either[ParseError, List[Token]] =
    dieParserService.parse(input)

  def prettyPrint(tokens: List[Token]) =
    tokens.map(token => tokenParser.prettyPrintToken(token)).mkString(" ")

  def execute(input: String): Either[ParseError, List[Result]] =
    for {
      tokens <- dieParserService.parse(input)
      res    <- dieParserService.eval(tokens)
    } yield res
}
