package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.results.ResultService
import io.github.ruixuantan.rocknroll.core.tokens.Operator.{
  Add,
  Separate,
  Subtract,
}
import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}
import io.github.ruixuantan.rocknroll.core.tokens.{DieService, NumberService}
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

  test("DieParserService parse d20 + 20 -2d6/ 3d2") {
    assert(
      dieParserService.parse("d20 + 20 - 2d6") ==
        Right(List(Die(20, 1), Add, Number(20), Subtract, Die(6, 2))),
    )
  }

  test("DieParserService parse errors") {
    assert(dieParserService.parse("d20 + 42.3") == Left(ParseTokenError))
  }

  test("DieParserService validate d20") {
    assert(dieParserService.validate(List(Die(20, 1))))
  }

  test("DieParserService validate 20") {
    assert(dieParserService.validate(List(Number(20))))
  }

  test("DieParserService validate 20 + 2d20 - 3 / d4 + 6") {
    assert(
      dieParserService.validate(
        List(
          Number(20),
          Add,
          Die(20, 2),
          Subtract,
          Number(3),
          Separate,
          Die(4, 1),
          Add,
          Number(6),
        ),
      ),
    )
  }

  test("DieParserService validate invalid 20 -") {
    assert(!dieParserService.validate(List(Number(20), Subtract)))
  }

  test("DieParserService validate invalid 20 - + 3d4") {
    assert(
      !dieParserService.validate(List(Number(20), Subtract, Add, Die(4, 3))),
    )
  }

  test("DieParserService eval d20") {

    assert(dieParserService.eval(List(Die(20, 1))) match {
      case Right(results) =>
        (results.map(_.input) == List("d20")) &
          (results.map(_.expected) == List(10.5)) &
          (results.map(_.standardDeviation) == List(5.766)) &
          (results.map(_.lowerBound) == List(1)) &
          (results.map(_.upperBound) == List(20)) &
          (results.map(_.diceRolled) == List(1))
      case Left(_) => false
    })
  }

  test("DieParserService eval 12") {
    assert(dieParserService.eval(List(Number(12))) match {
      case Right(results) =>
        (results.map(_.input) == List("12")) &
          (results.map(_.expected) == List(12)) &
          (results.map(_.standardDeviation) == List(0)) &
          (results.map(_.lowerBound) == List(12)) &
          (results.map(_.upperBound) == List(12)) &
          (results.map(_.diceRolled) == List(0))
      case Left(_) => false
    })
  }

  test("DieParserService eval d20 + 20 - 2d6") {
    assert(
      dieParserService.eval(
        List(Die(20, 1), Add, Number(20), Subtract, Die(6, 2)),
      ) match {
        case Right(results) =>
          (results.map(_.input) == List("d20 + 20 - 2d6")) &
            (results.map(_.expected) == List(23.5)) &
            (results.map(_.standardDeviation) == List(6.252)) &
            (results.map(_.lowerBound) == List(9)) &
            (results.map(_.upperBound) == List(38)) &
            (results.map(_.diceRolled) == List(3))
        case Left(_) => false
      },
    )
  }

  test("DieParserService eval 1 - d20") {
    assert(
      dieParserService.eval(
        List(Number(1), Subtract, Die(20, 1)),
      ) match {
        case Right(results) =>
          (results.map(_.input) == List("1 - d20")) &
            (results.map(_.expected) == List(-9.5)) &
            (results.map(_.standardDeviation) == List(5.766)) &
            (results.map(_.lowerBound) == List(-19)) &
            (results.map(_.upperBound) == List(0)) &
            (results.map(_.diceRolled) == List(1))
        case Left(_) => false
      },
    )
  }

  test("DieParserService eval d20/3d6 + 8") {
    assert(
      dieParserService.eval(
        List(Die(20, 1), Separate, Die(6, 3), Add, Number(8)),
      ) match {
        case Right(results) =>
          (results.map(_.input) == List("d20", "3d6 + 8")) &
            (results.map(_.expected) == List(10.5, 18.5)) &
            (results.map(_.standardDeviation) == List(5.766, 2.958)) &
            (results.map(_.lowerBound) == List(1, 11)) &
            (results.map(_.upperBound) == List(20, 26)) &
            (results.map(_.diceRolled) == List(1, 3))
        case Left(_) => false
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
