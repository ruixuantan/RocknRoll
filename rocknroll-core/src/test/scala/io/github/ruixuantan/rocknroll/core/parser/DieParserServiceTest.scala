package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.tokens.Operator.{Add, Subtract}
import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}
import io.github.ruixuantan.rocknroll.core.tokens.{
  DieService,
  NumberService,
  ResultService,
}
import org.scalatest.funsuite.AnyFunSuite

class DieParserServiceTest extends AnyFunSuite {
  private val tokenParser      = TokenParser()
  private val resultService    = ResultService(DieService(), NumberService())
  private val dieParserService = DieParserService(tokenParser, resultService)

  test("DieParserService parse d20") {
    assert(dieParserService.parse("d20") == Right(List(Die(20, 1))))
  }

  test("DieParserService parse 20") {
    assert(dieParserService.parse("20") == Right(List(Number(20))))
  }

  test("DieParserService parse d20 + 20 - 2d6") {
    assert(
      dieParserService.parse("d20 + 20 - 2d6") ==
        Right(List(Die(20, 1), Add, Number(20), Subtract, Die(6, 2))),
    )
  }

  test("DieParserService parse errors") {
    assert(dieParserService.parse("d20 + 42.3") == Left(ParseTokenError))
  }

  test("DieParserService eval d20") {
    assert(dieParserService.eval(List(Die(20, 1))) match {
      case Right(res) => res.expected == 10.5
      case Left(_)    => false
    })
  }

  test("DieParserService eval 12") {
    assert(dieParserService.eval(List(Number(12))) match {
      case Right(res) => res.expected == 12
      case Left(_)    => false
    })
  }

  test("DieParserService eval d20 + 20 - 2d6") {
    assert(
      dieParserService.eval(
        List(Die(20, 1), Add, Number(20), Subtract, Die(6, 2)),
      ) match {
        case Right(res) => res.expected == 23.5
        case Left(_)    => false
      },
    )
  }

  test("DieParserService eval 1 - d20") {
    assert(
      dieParserService.eval(
        List(Number(1), Subtract, Die(20, 1)),
      ) match {
        case Right(res) => res.expected == -9.5
        case Left(_)    => false
      },
    )
  }

  test("DieParserService eval wrong value order") {
    assert(
      dieParserService.eval(
        List(Number(1), Die(20, 1)),
      ) match {
        case Right(_)  => false
        case Left(err) => err == ParseOrderError
      },
    )
  }

  test("DieParserService eval wrong operator order") {
    assert(
      dieParserService.eval(
        List(Number(1), Add, Subtract, Die(20, 1)),
      ) match {
        case Right(_)  => false
        case Left(err) => err == ParseOrderError
      },
    )
  }
}
