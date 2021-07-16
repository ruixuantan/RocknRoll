package io.github.ruixuantan.rocknroll.core

import io.github.ruixuantan.rocknroll.core.generators.Generator
import io.github.ruixuantan.rocknroll.core.parser.{
  DieParserAlgebra,
  DieParserService,
  FinalResult,
  ParseError,
  TokenParser,
}
import io.github.ruixuantan.rocknroll.core.tokens.Token

class GeneratorService(generator: Generator) extends GeneratorAlgebra {
  val tokenParser: TokenParser           = TokenParser()
  val dieParserService: DieParserAlgebra = DieParserService(tokenParser)

  override def getGeneratorName: String = generator.getName

  override def eval(
      tokens: List[Token],
  ): Either[ParseError, List[FinalResult]] =
    dieParserService.eval(tokens, generator)
}

object GeneratorService {
  def apply(generator: Generator): GeneratorAlgebra = new GeneratorService(generator)
}
