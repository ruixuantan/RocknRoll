package io.github.ruixuantan.rocknroll.core.generators

import org.scalatest.funsuite.AnyFunSuite

class SimpleGeneratorTest extends AnyFunSuite {
  test("Simple Generator Test d3") {
    val range          = 3
    val rng_0          = SimpleGenerator(0)
    val (val_1, rng_1) = rng_0.nextInt(range)
    assert(val_1 == 1)
    val (val_2, rng_2) = rng_1.nextInt(range)
    assert(val_2 == 2)
    val (val_3, rng_3) = rng_2.nextInt(range)
    assert(val_3 == 3)
    val (val_4, _) = rng_3.nextInt(range)
    assert(val_4 == 1)
  }
}
