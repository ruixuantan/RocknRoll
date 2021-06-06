package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.dice.Die
import org.scalatest.funsuite.AnyFunSuite

class RollParserServiceTest extends AnyFunSuite {
  private val service = RollParserService()

  test("RollParserService.parse d6") {
    assert(service.parse("d6") == Right(List(Die(6, 1))))
  }

  test("RollParserService.parse 2d4, 8d7") {
    assert(service.parse("2d4 8d7") == Right(List(Die(4, 2), Die(7, 8))))
  }

  test("RollParserService.parse die side not specified") {
    assert(service.parse("2d6, 6dE") == Left(RollParserError))
  }

  test("RollParserService.parse invalid string") {
    assert(service.parse("invalid") == Left(RollParserError))
  }
}
