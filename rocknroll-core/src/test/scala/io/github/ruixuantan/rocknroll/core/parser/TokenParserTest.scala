package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.tokens.Operator.{
  Add,
  Separate,
  Subtract,
}
import io.github.ruixuantan.rocknroll.core.tokens.Token
import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.mutable.ListBuffer

class TokenParserTest extends AnyFunSuite {
  private val tokenParser = TokenParser()

  test("TokenParser tokenize d8 die") {
    assert(tokenParser.tokenizeValue("d8") == Right(Die(8, 1)))
  }

  test("TokenParser tokenize 2d18 die") {
    assert(tokenParser.tokenizeValue("2d18") == Right(Die(18, 2)))
  }

  test("TokenParser tokenize number 23") {
    assert(tokenParser.tokenizeValue("23") == Right(Number(23)))
  }

  test("TokenParser tokenize add operator") {
    assert(tokenParser.tokenizeOperator("+") == Right(Add))
  }

  test("TokenParser tokenize subtract operator") {
    assert(tokenParser.tokenizeOperator("-") == Right(Subtract))
  }

  test("TokenParser tokenize separate operator") {
    assert(tokenParser.tokenizeOperator("/") == Right(Separate))
  }

  test("TokenParser tokenize errors") {
    assert(tokenParser.tokenizeValue("*") == Left(ParseTokenError))
    assert(tokenParser.tokenizeValue("0d9") == Left(ParseTokenError))
    assert(tokenParser.tokenizeValue("10d0") == Left(ParseTokenError))
    assert(tokenParser.tokenizeValue("3dd7") == Left(ParseTokenError))
    assert(tokenParser.tokenizeValue("03d20") == Left(ParseTokenError))
    assert(tokenParser.tokenizeValue("23.6") == Left(ParseTokenError))
  }

  test("TokenParser parse d12") {
    assert(
      tokenParser.parse("d12") match {
        case Right(ls) => ls == List(Die(12, 1))
        case Left(_)   => false
      },
    )
  }

  test("TokenParser parse 12") {
    assert(
      tokenParser.parse("12") match {
        case Right(ls) => ls == List(Number(12))
        case Left(_)   => false
      },
    )
  }

  test("TokenParser parse d12+3/9-2d6") {
    assert(
      tokenParser.parse("d12+3/9-2d6") match {
        case Right(ls) =>
          ls == List(
            Die(12, 1),
            Add,
            Number(3),
            Separate,
            Number(9),
            Subtract,
            Die(6, 2),
          )
        case Left(_) => false
      },
    )
  }
}
