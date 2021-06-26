package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.results.{Result, ResultService}
import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}
import org.scalatest.funsuite.AnyFunSuite

class ResultServiceTest extends AnyFunSuite {
  private val dieService    = DieService()
  private val numberService = NumberService()
  private val resultService = ResultService(dieService, numberService)

  test("ResultService getResult of d15") {
    val res = resultService.getResult(Die(15, 1))
    assert(res == Result(res.result, 8, 18.6667, 1, 15, 1))
  }

  test("ResultService getResult of 17d21") {
    val res = resultService.getResult(Die(21, 17))
    assert(res == Result(res.result, 187, 623.3333, 17, 357, 17))
  }

  test("ResultService getResult of 21") {
    val res = resultService.getResult(Number(21))
    assert(res == Result(21, 21, 0, 21, 21, 0))
  }

  test("ResultService add result") {
    val x = Result(21, 17, 3.6, 2, 3, 1)
    val y = Result(34, 2.5, 6, 3, 4, 5)
    assert(resultService.add(x)(y) == Result(55, 19.5, 9.6, 5, 7, 6))
  }

  test("ResultService subtract result") {
    val x = Result(21, 17, 3.6, 2, 3, 6)
    val y = Result(34, 2.5, 6, 3, 4, 2)
    assert(resultService.subtract(x)(y) == Result(-13, 14.5, 9.6, -2, 0, 8))
  }
}
