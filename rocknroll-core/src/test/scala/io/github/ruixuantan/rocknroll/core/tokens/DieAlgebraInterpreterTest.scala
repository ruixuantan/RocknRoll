package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.results.Result
import io.github.ruixuantan.rocknroll.core.tokens.Value.Die
import org.scalatest.funsuite.AnyFunSuite

class DieAlgebraInterpreterTest extends AnyFunSuite {
  private val service = DieAlgebraInterpreter()

  test("DieService roll single d12") {
    val res = service.getResult(Die(12, 1))
    assert(res == Result(res.result, 6.5, 11.9167, 1, 12, 1))
  }

  test("DieService roll d4 5 times") {
    val res = service.getResult(Die(4, 5))
    assert(res == Result(res.result, 12.5, 6.25, 5, 20, 5))
  }
}
