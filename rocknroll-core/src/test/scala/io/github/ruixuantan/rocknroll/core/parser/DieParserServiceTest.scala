package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.generators.TestGenerator
import io.github.ruixuantan.rocknroll.core.tokens.Operator.{Add, Separate, Subtract}
import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}
import org.scalatest.funsuite.AnyFunSuite

class DieParserServiceTest extends AnyFunSuite {
  private val tokenParser      = TokenParser()
  private val dieParserService = DieParserService(tokenParser)
  private val generator        = TestGenerator()

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
    assert(dieParserService.eval(List(Die(20, 1)), generator) match {
      case Right(results) => results == List(FinalResult("d20", 1, 10.5, 5.766, 1, 20, 1))
      case Left(_)        => false
    })
  }

  test("DieParserService eval 12") {
    assert(dieParserService.eval(List(Number(12)), generator) match {
      case Right(results) => results == List(FinalResult("12", 12, 12, 0, 12, 12, 0))
      case Left(_)        => false
    })
  }

  test("DieParserService eval d20 + 20 - 2d6") {
    assert(
      dieParserService.eval(List(Die(20, 1), Add, Number(20), Subtract, Die(6, 2)), generator) match {
        case Right(results) => results == List(FinalResult("d20 + 20 - 2d6", 19, 23.5, 6.252, 9, 38, 3))
        case Left(_)        => false
      },
    )
  }

  test("DieParserService eval 1 - d20") {
    assert(
      dieParserService.eval(List(Number(1), Subtract, Die(20, 1)), generator) match {
        case Right(results) => results == List(FinalResult("1 - d20", 0, -9.5, 5.766, -19, 0, 1))
        case Left(_)        => false
      },
    )
  }

  test("DieParserService eval d20/3d6 + 8") {
    assert(
      dieParserService.eval(List(Die(20, 1), Separate, Die(6, 3), Add, Number(8)), generator) match {
        case Right(results) =>
          results == List(
            FinalResult("d20", 1, 10.5, 5.766, 1, 20, 1),
            FinalResult("3d6 + 8", 11, 18.5, 2.958, 11, 26, 3),
          )
        case Left(_) => false
      },
    )
  }

  test("DieParserService eval wrong value order") {
    assert(
      dieParserService.eval(List(Number(1), Die(20, 1)), generator) match {
        case Right(_)  => false
        case Left(err) => err == ParseOrderError
      },
    )
  }

  test("DieParserService eval wrong operator order") {
    assert(
      dieParserService.eval(List(Number(1), Add, Subtract, Die(20, 1)), generator) match {
        case Right(_)  => false
        case Left(err) => err == ParseOrderError
      },
    )
  }
}
