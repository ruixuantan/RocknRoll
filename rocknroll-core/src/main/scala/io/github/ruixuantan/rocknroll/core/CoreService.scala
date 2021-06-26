package io.github.ruixuantan.rocknroll.core

import io.github.ruixuantan.rocknroll.core.parser.{
  DieParserAlgebra,
  DieParserService,
  FinalResult,
  ParseError,
  TokenParser,
}
import io.github.ruixuantan.rocknroll.core.results.{
  ResultAlgebra,
  ResultService,
}
import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}
import io.github.ruixuantan.rocknroll.core.tokens.{
  DieService,
  NumberService,
  Token,
  ValueAlgebra,
}

object CoreService extends CoreAlgebra {

  val dieService: ValueAlgebra[Die]       = DieService()
  val numberService: ValueAlgebra[Number] = NumberService()
  val resultService: ResultAlgebra        = ResultService(dieService, numberService)
  val tokenParser: TokenParser            = TokenParser()
  val dieParserService: DieParserAlgebra =
    DieParserService(tokenParser, resultService)

  override def parse(input: String): Either[ParseError, List[Token]] =
    dieParserService.parse(input)

  override def validate(tokens: List[Token]): Boolean =
    dieParserService.validate(tokens)

  override def eval(
      tokens: List[Token],
  ): Either[ParseError, List[FinalResult]] =
    dieParserService.eval(tokens)

  override def prettyPrint(
      tokens: List[Token],
  ): String =
    tokenParser.prettyPrintTokens(tokens)

  override def getDice(tokens: List[Token]): List[Die] =
    tokens.filter(_.isInstanceOf[Die]).map(_.asInstanceOf[Die])
}
