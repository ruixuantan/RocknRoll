package io.github.ruixuantan.rocknroll.core.generators

import scala.util.Random

class DefaultGenerator extends Generator {
  private val rng = new Random()

  override def nextInt(range: Int): (Int, Generator) =
    (rng.nextInt(range) + 1, DefaultGenerator())
}

object DefaultGenerator {
  def apply(): DefaultGenerator = new DefaultGenerator()
}
