package io.github.ruixuantan.rocknroll.core.generators

import scala.util.Random

object DefaultGenerator extends Generator {
  private val rng = new Random()

  override def nextInt(range: Int): Int =
    rng.nextInt(range) + 1
}
