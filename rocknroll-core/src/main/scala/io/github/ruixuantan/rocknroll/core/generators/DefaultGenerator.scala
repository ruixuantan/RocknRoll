package io.github.ruixuantan.rocknroll.core.generators

import scala.util.Random

class DefaultGenerator extends Generator {
  private val rng = new Random()

  override def getName: String = "default"

  override def nextInt(range: Int): Int =
    rng.nextInt(range) + 1
}

object DefaultGenerator {
  def apply(): DefaultGenerator = new DefaultGenerator()
}
