package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.generators.{
  DefaultGenerator,
  Generator,
}
import io.github.ruixuantan.rocknroll.core.tokens.Value.Die

class DieService(generator: Generator) {
  private def getExpected(die: Die): Double =
    ((die.sides + 1) / 2.toDouble) * die.freq

  private def rollOnce(die: Die): Int =
    generator.nextInt(die.sides)

  def roll(die: Die): Result = {
    val res = (1 to die.freq).map(_ => rollOnce(die)).sum
    Result(res, getExpected(die))
  }
}

object DieService {
  def apply(generator: Generator = DefaultGenerator) =
    new DieService(generator)
}
