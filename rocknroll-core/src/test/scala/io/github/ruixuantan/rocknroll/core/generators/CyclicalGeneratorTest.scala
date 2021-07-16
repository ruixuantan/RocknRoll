package io.github.ruixuantan.rocknroll.core.generators

import org.scalatest.funsuite.AnyFunSuite

class CyclicalGeneratorTest extends AnyFunSuite {
  test("Cyclical Generator Test d3") {
    val range = 3
    val rng   = CyclicalGenerator(0)
    assert(rng.nextInt(range) == 1)
    assert(rng.nextInt(range) == 2)
    assert(rng.nextInt(range) == 3)
    assert(rng.nextInt(range) == 1)
  }

  test("Cyclical Generator test negative seed") {
    val value = CyclicalGenerator(-3).nextInt(3)
    assert(value == 1)
  }
}
