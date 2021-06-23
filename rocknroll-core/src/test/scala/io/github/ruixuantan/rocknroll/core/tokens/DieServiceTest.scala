package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.tokens.Value.Die
import org.scalatest.funsuite.AnyFunSuite

class DieServiceTest extends AnyFunSuite {
  private val service = DieService()

  test("DieService roll single d12") {
    val d12 = Die(12, 1)
    assert(service.getResult(d12).expected == 6.5)
    assert(service.getResult(d12).variance == 11.9167)
  }

  test("DieService roll d4 5 times") {
    val d4 = Die(4, 5)
    assert(service.getResult(d4).expected == 12.5)
    assert(service.getResult(d4).variance == 6.25)
  }
}
