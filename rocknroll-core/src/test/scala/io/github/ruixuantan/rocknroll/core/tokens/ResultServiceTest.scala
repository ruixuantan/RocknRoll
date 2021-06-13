package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}
import org.scalatest.funsuite.AnyFunSuite

class ResultServiceTest extends AnyFunSuite {
  private val dieService    = DieService()
  private val numberService = NumberService()
  private val resultService = ResultService(dieService, numberService)

  test("ResultService getResult of d15") {
    val die = Die(15, 1)
    assert(resultService.getResult(die).expected == 8)
  }

  test("ResultService getResult of 17d21") {
    val die = Die(21, 17)
    assert(resultService.getResult(die).expected == 187)
  }

  test("ResultService getResult of 21") {
    val number = Number(21)
    assert(resultService.getResult(number).res == 21)
    assert(resultService.getResult(number).expected == 21)
  }

  test("ResultService add result") {
    val x = Result(21, 17)
    val y = Result(34, 2.5)
    assert(resultService.add(x)(y) == Result(55, 19.5))
  }

  test("ResultService subtract result") {
    val x = Result(21, 17)
    val y = Result(34, 2.5)
    assert(resultService.subtract(x)(y) == Result(-13, 14.5))
  }
}
