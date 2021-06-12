package io.github.ruixuantan.rocknroll.core

import io.github.ruixuantan.rocknroll.core.parser.{DieParserService, ParseError}
import io.github.ruixuantan.rocknroll.core.tokens.{
  DieService,
  NumberService,
  Result,
  ResultService,
}

object Service {
  def execute(input: String): Either[ParseError, Result] = {
    val diceService      = DieService()
    val numberService    = NumberService()
    val resultService    = ResultService(diceService, numberService)
    val dieParserService = DieParserService(resultService)
    for {
      tokens <- dieParserService.parse(input)
      res    <- dieParserService.eval(tokens)
    } yield res
  }
}