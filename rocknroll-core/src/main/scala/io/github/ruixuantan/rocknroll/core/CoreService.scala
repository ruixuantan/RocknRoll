package io.github.ruixuantan.rocknroll.core

import io.github.ruixuantan.rocknroll.core.parser.{DieParserAlgebra, DieParserService, ParseError, TokenParser}
import io.github.ruixuantan.rocknroll.core.tokens.Value.Die
import io.github.ruixuantan.rocknroll.core.tokens.Token

object CoreService extends CoreAlgebra {

  val tokenParser: TokenParser           = TokenParser()
  val dieParserService: DieParserAlgebra = DieParserService(tokenParser)

  override def parse(input: String): Either[ParseError, List[Token]] =
    dieParserService.parse(input)

  override def validate(tokens: List[Token]): Boolean =
    dieParserService.validate(tokens)

  override def prettyPrint(
      tokens: List[Token],
  ): String =
    tokenParser.prettyPrintTokens(tokens)

  override def getDice(tokens: List[Token]): List[Die] =
    tokens.filter(_.isInstanceOf[Die]).map(_.asInstanceOf[Die])
}
