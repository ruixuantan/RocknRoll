package io.github.ruixuantan.rocknroll.core.expressions

import io.github.ruixuantan.rocknroll.core.tokens.DieService
import io.github.ruixuantan.rocknroll.core.tokens.Value.Die
import org.scalatest.funsuite.AnyFunSuite

class DieServiceTest extends AnyFunSuite {
  private val service = DieService()

  test("DieService roll single d12") {
    val d12 = Die(12, 1)
    assert(service.roll(d12).expected == 6.5)
  }

  test("DieService roll d4 5 times") {
    val d4 = Die(4, 5)
    assert(service.roll(d4).expected == 12.5)
  }
}