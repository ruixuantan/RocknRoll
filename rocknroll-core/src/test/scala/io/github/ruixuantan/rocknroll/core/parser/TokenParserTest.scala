package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.tokens.Operator.{Add, Subtract}
import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}
import org.scalatest.funsuite.AnyFunSuite

class TokenParserTest extends AnyFunSuite {
  private val tokenParser = TokenParser()

  test("TokenParser tokenize d8 die") {
    assert(tokenParser.tokenize("d8") == Right(Die(8, 1)))
  }

  test("TokenParser tokenize 2d18 die") {
    assert(tokenParser.tokenize("2d18") == Right(Die(18, 2)))
  }

  test("TokenParser tokenize number 23") {
    assert(tokenParser.tokenize("23") == Right(Number(23)))
  }

  test("TokenParser tokenize add operator") {
    assert(tokenParser.tokenize("+") == Right(Add))
  }

  test("TokenParser tokenize subtract operator") {
    assert(tokenParser.tokenize("-") == Right(Subtract))
  }

  test("TokenParser tokenize errors") {
    assert(tokenParser.tokenize("*") == Left(ParseTokenError))
    assert(tokenParser.tokenize("0d9") == Left(ParseTokenError))
    assert(tokenParser.tokenize("10d0") == Left(ParseTokenError))
    assert(tokenParser.tokenize("3dd7") == Left(ParseTokenError))
    assert(tokenParser.tokenize("03d20") == Left(ParseTokenError))
    assert(tokenParser.tokenize("23.6") == Left(ParseTokenError))
  }
}
