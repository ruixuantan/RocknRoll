package io.github.ruixuantan.rocknroll.core.parser

import org.scalatest.funsuite.AnyFunSuite
import io.github.ruixuantan.rocknroll.core.commands.{
  InvalidCommand,
  RollCommand,
}
import io.github.ruixuantan.rocknroll.core.dice.Die

class ParserServiceTest extends AnyFunSuite {
  val parserService = ParserService()

  test("ParserService single die test") {
    assert(parserService.parse("d6") == RollCommand(List(Die(6, 1))))
  }

  test("ParserService multiple die test") {
    assert(
      parserService.parse("d6 10d9") == RollCommand(
        List(Die(6, 1), Die(9, 10)),
      ),
    )
  }

  test("ParserService invalid die input") {
    assert(
      parserService.parse("10d5 3dD") == InvalidCommand(
        "Cannot parse input string",
      ),
    )
  }

  test("ParserService invalid input") {
    assert(
      parserService.parse("invalid") == InvalidCommand(
        "Cannot parse input string",
      ),
    )
  }
}
